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
        Optional<Product> existingProduct = productRepo.findById(productDTO.getProductID());
        if (existingProduct.isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        Category category = categoryRepo.findByName(productDTO.getCategory().getCategoryName());
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        product.setSellingPrice(productDTO.getLaborCost()*productDTO.getMarkupRate());
        productRepo.save(product);
        return modelMapper.map(product,new TypeToken<ProductDTO>() {}.getType());
    }

    public void deleteProduct(int id) {
        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepo.delete(existingProduct.get());
    }

    public List<ProductDTO> findByName(String name) {
        List<Product> products = productRepo.findByProductName(name);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
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
        Product existingProduct = productRepo.findById(id).get();
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setInventory(productDTO.getInventory());
        existingProduct.setWarrantyPeriod(productDTO.getWarrantyPeriod());
        existingProduct.setSize(productDTO.getSize());
        existingProduct.setImageURL(productDTO.getImageURL());
        existingProduct.setLaborCost(productDTO.getLaborCost());
        existingProduct.setMarkupRate(productDTO.getMarkupRate());
        existingProduct.setSellingPrice(productDTO.getLaborCost()*productDTO.getMarkupRate());
        productRepo.save(existingProduct);
        return modelMapper.map(productRepo.save(existingProduct), ProductDTO.class);
    }

}
