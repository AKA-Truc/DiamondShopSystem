package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.repositories.CategoryRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product existingProduct = productRepo.findByProductName(product.getProductName());
        if (existingProduct != null) {
            throw new RuntimeException("Product already exists");
        }
        Category existingCategory = categoryRepo.findById(product.getCategory()
                .getCategoryID()).orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(existingCategory);
        product.setSellingPrice(product.getLaborCost() * productDTO.getMarkupRate());
        productRepo.save(product);
        return modelMapper.map(product,ProductDTO.class);
    }

    public void deleteProduct(int id) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepo.delete(existingProduct.get());
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

    public ProductDTO updateProduct(int id,ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setInventory(product.getInventory());
        existingProduct.setWarrantyPeriod(product.getWarrantyPeriod());
        existingProduct.setSize(product.getSize());
        existingProduct.setImageURL(product.getImageURL());
        existingProduct.setLaborCost(product.getLaborCost());
        existingProduct.setMarkupRate(product.getMarkupRate());
        existingProduct.setSellingPrice(product.getLaborCost()*product.getMarkupRate());
        productRepo.save(existingProduct);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }

}
