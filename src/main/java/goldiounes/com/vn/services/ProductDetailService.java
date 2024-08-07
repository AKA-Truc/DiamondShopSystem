package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entity.ProductDetail;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

//package goldiounes.com.vn.services;
//
//import goldiounes.com.vn.models.entity.ProductDetail;
//import goldiounes.com.vn.repositories.ProductDetailRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ProductDetailService {
//    @Autowired
//    private ProductDetailRepo productDetailRepo;
//
//    public List<ProductDetail> findAll() {
//        return productDetailRepo.findAll();
//    }
//
//    public ProductDetail findById(int id) {
//        Optional<ProductDetail> optionalProductDetail = productDetailRepo.findById(id);
//        if (!optionalProductDetail.isPresent()) {
//            throw new RuntimeException("ProductDetail not found");
//        }
//        return optionalProductDetail.get();
//    }
//
//    public ProductDetail save(ProductDetail productDetail) {
//        return productDetailRepo.save(productDetail);
//    }
//
//    public void deleteById(int id) {
//        if (!productDetailRepo.existsById(id)) {
//            throw new RuntimeException("ProductDetail not found");
//        }
//        productDetailRepo.deleteById(id);
//    }
//
