
package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.*;
import goldiounes.com.vn.models.entities.CartItem;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.repositories.CategoryRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CartItemService cartItemService;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo, ModelMapper modelMapper, FileUploadService fileUploadService, ProductDetailService productDetailService, OrderDetailService orderDetailService) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
        this.fileUploadService = fileUploadService;
        this.productDetailService = productDetailService;
        this.orderDetailService = orderDetailService;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        List<Product> activeProduct = new ArrayList<>();
        for (Product product : products) {
            if(product.getStatus().equals("active")){
                activeProduct.add(product);
            }
        }
        return modelMapper.map(activeProduct, new TypeToken<List<ProductDTO>>() {}.getType());
    }

    public ProductDTO getProduct(int id) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No product found"));
        if(existingProduct.getStatus().equals("inactive")){
            throw new RuntimeException("Product is already inactive");
        }
        return modelMapper.map(existingProduct, ProductDTO.class);
    }

    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile imageFile,MultipartFile subImageURL) throws IOException {
        Product product = modelMapper.map(productDTO, Product.class);

        Category existingCategory = categoryRepo.findById(productDTO.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(existingCategory);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageURL = fileUploadService.uploadImage(imageFile);
            product.setImageURL(imageURL);
        }

        if (subImageURL != null && !subImageURL.isEmpty()) {
            String imageURL = fileUploadService.uploadImage(subImageURL);
            product.setSubImageURL(imageURL);
        }

        product.setStatus("active");

        productRepo.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }


    public boolean deleteProduct(int id) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findByProductId(existingProduct.getProductID());
        if (orderDetailDTOS.isEmpty()) {
            existingProduct.setStatus("inactive");
            productRepo.save(existingProduct);
            return true;
        }
        else{
            for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {
                OrderDTO orderDTO = orderDetailDTO.getOrder();
                if(orderDTO != null && !orderDTO.getStatus().equals("Final")){
                   return false;
                }
            }
        }
        List<CartItemDTO> cartItems = cartItemService.findByProductId(existingProduct.getProductID());
        if (cartItems.isEmpty()) {
            existingProduct.setStatus("inactive");
            productRepo.save(existingProduct);
            return true;
        }
        else {
            for (CartItemDTO cartItemDTO : cartItems) {
                if(!cartItemService.deleteByID(cartItemDTO.getCartItemId())){
                    return false;
                }
            }
        }
        existingProduct.setStatus("inactive");
        productRepo.save(existingProduct);
        return true;
    }

    public void deleteById(int id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public List<ProductDTO> findByCategoryAndPrice(String keyWord, double minPrice, double maxPrice) {
        List<Product> products = productRepo.findByCategoryKeyWord(keyWord);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        List<Product> activeProduct = new ArrayList<>();
        for (Product product : products) {
            if(product.getStatus().equals("active")){
                if(productDetailService.checkProductDetail(product.getProductID())){
                    ProductDetailDTO productDetailDTO = productDetailService.getMinProductDetailByProductId(product.getProductID());
                    if(minPrice <= productDetailDTO.getSellingPrice() && productDetailDTO.getSellingPrice() <= maxPrice){
                        activeProduct.add(product);
                    }
                }
            }
        }
        return modelMapper.map(activeProduct, new TypeToken<List<ProductDTO>>() {}.getType());
    }

    public ProductDTO updateProduct(int id, ProductDTO productDTO, MultipartFile imageFile, MultipartFile subImageURL) throws IOException {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Ánh xạ các trường từ DTO sang entity nhưng không ghi đè các trường imageURL và subImageURL
        Product product = modelMapper.map(productDTO, Product.class);

        // Cập nhật category
        Category existingCategory = categoryRepo.findById(product.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingProduct.setCategory(existingCategory);

        // Chỉ cập nhật imageURL nếu ảnh mới được truyền vào
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageURL = fileUploadService.uploadImage(imageFile);
            existingProduct.setImageURL(imageURL);
        }

        // Chỉ cập nhật subImageURL nếu ảnh mới được truyền vào
        if (subImageURL != null && !subImageURL.isEmpty()) {
            String subImageUrl = fileUploadService.uploadImage(subImageURL);
            existingProduct.setSubImageURL(subImageUrl);
        }

        existingProduct.setProductName(product.getProductName());
        existingProduct.setWarrantyPeriod(product.getWarrantyPeriod());
        productRepo.save(existingProduct);

        return modelMapper.map(existingProduct, ProductDTO.class);
    }

}
