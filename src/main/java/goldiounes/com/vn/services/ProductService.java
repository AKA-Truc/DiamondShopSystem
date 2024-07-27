package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Product;
import goldiounes.com.vn.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> findAll() {
        return productRepo.findAll();
    }
    public Product findById(int id) {
        return productRepo.findById(id).get();
    }
    public Product save(Product product) {
        return productRepo.save(product);
    }
    public void delete(Product product) {
        productRepo.delete(product);
    }
    public void deleteById(int id) {
        productRepo.deleteById(id);
    }
    public Product findByName(String name) {
        return productRepo.findByName(name);
    }
}
