package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Product;
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
    public Product createProduct(@RequestBody Product product) {
        List<Product> Products = productService.findByName(product.getProductName());
        if(Products.isEmpty()) {
            return productService.save(product);
        }
        throw new RuntimeException("Đã Tồn Tại");
    }

    @GetMapping("/products")
    public List<Product> getAllProduct() {
        List<Product> Products = productService.findAll();
        if(Products.isEmpty()) {
            throw new RuntimeException("Product List is Empty");
        }
        return Products;
    }


    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id) {
        Product existingProduct = productService.findById(id);
        if(existingProduct == null) {
            throw new RuntimeException("Product Not Found");
        }
        return existingProduct;
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        Product existingProduct = productService.findById(id);
        if(existingProduct == null) {
            throw new RuntimeException("Product Not Found");
        }
        productService.delete(existingProduct);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        existingProduct.setProductName(product.getProductName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setCartItems(product.getCartItems());
        existingProduct.setImageURL(product.getImageURL());
        existingProduct.setInventory(product.getInventory());
        existingProduct.setLaborCost(product.getLaborCost());
        existingProduct.setMarkupRate(product.getMarkupRate());
        existingProduct.setOrderDetails(product.getOrderDetails());
        existingProduct.setProductDetails(product.getProductDetails());
        existingProduct.setReceipt(product.getReceipt());
        existingProduct.setSellingPrice(product.getMarkupRate()*product.getLaborCost());
        existingProduct.setWarrantyPeriod(product.getWarrantyPeriod());
        return productService.save(existingProduct);
    }
}