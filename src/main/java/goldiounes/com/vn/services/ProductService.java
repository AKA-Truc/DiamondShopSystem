
package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.repositories.CategoryRepo;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
    }

    public ProductDTO getProduct(int id) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return modelMapper.map(existingProduct.get(), ProductDTO.class);
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

        productRepo.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }


    public boolean deleteProduct(int id) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        for (ProductDetail productDetail : existingProduct.getProductDetails()) {
            if(productDetailService.findById(productDetail.getProductDetailID()) == null){
                throw new RuntimeException("Product detail not found");
            }
            productDetailService.deleteById(productDetail.getProductDetailID());
        }
        productRepo.deleteById(existingProduct.getProductID());
        return true;
    }

    public void deleteById(int id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public List<ProductDTO> findByCategory(String keyWord) {
        List<Product> products = productRepo.findByCategoryKeyWord(keyWord);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
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
