package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.ProductDetailDTO;
import goldiounes.com.vn.models.entity.ProductDetail;
import goldiounes.com.vn.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productdetail-management")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/productdetails")
    public List<ProductDetailDTO> getAllProductDetails() {
        return productDetailService.findAll();
    }

    @GetMapping("/productdetails/{id}")
    public ProductDetailDTO getProductDetailById(@PathVariable int id) {
        return productDetailService.findById(id);
    }

    @PostMapping("/productdetails/{id}")
    public ProductDetailDTO createProductDetail(@RequestBody ProductDetail productDetail) {
        return productDetailService.createProductDetail(productDetail);
    }

//    @PutMapping("/productdetails/{id}")
//    public ProductDetailDTO updateProductDetail(@PathVariable int id, @RequestBody ProductDetail productDetail) {
//        productDetail.setProductDetailID(id);
//        return productDetailService.save(productDetail);
//    }

    @DeleteMapping("/productdetails/{id}")
    public void deleteProductDetail(@PathVariable int id) {
        productDetailService.deleteById(id);
    }
}
