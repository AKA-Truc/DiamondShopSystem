package goldiounes.com.vn.services;

import goldiounes.com.vn.models.ProductDetail;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepo productDetailRepo;

    public List<ProductDetail> findAll() {
        return productDetailRepo.findAll();
    }
    public ProductDetail findById(int id) {
        return productDetailRepo.findById(id).get();
    }
    public ProductDetail save(ProductDetail ProductDetail) {
        return productDetailRepo.save(ProductDetail);
    }
    public void deleteById(int id) {
        productDetailRepo.deleteById(id);
    }
}
