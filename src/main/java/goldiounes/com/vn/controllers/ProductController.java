package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.ProductDTO;
import goldiounes.com.vn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-management")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping("/products/category/{keyword}")
    public List<ProductDTO> getProductsByKeyword(@PathVariable String keyword) {
       return productService.findByCategory(keyword);
    }

    @GetMapping("/products")
    public List<ProductDTO> getAllProduct() {
        return productService.getAllProducts();
    }


    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }



    @PutMapping("/products/{id}")
    public ProductDTO updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }
}
