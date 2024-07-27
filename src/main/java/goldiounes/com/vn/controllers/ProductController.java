package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Product;
import goldiounes.com.vn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        List<Product> Products = productService.findByName(product.getProductName());
        if(Products.isEmpty()) {
            return productService.save(product);
        }
        throw new RuntimeException("Đã Tồn Tại");
    }

    @GetMapping("/getAll")
    public List<Product> getAllProduct() {
        List<Product> Products = productService.findAll();
        if(Products.isEmpty()) {
            throw new RuntimeException("Product List is Empty");
        }
        return Products;
    }


    @GetMapping("/get/{id}")
    public Product getProduct(@PathVariable int id) {
        Product existingProduct = productService.findById(id);
        if(existingProduct == null) {
            throw new RuntimeException("Product Not Found");
        }
        return existingProduct;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        Product existingProduct = productService.findById(id);
        if(existingProduct == null) {
            throw new RuntimeException("Product Not Found");
        }
        productService.delete(existingProduct);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found");
        }
        existingProduct.setProductName(existingProduct.getProductName());
        existingProduct.setCategory(existingProduct.getCategory());
        existingProduct.setCartItems(existingProduct.getCartItems());
        existingProduct.setImageURL(existingProduct.getImageURL());
        existingProduct.setInventory(existingProduct.getInventory());
        existingProduct.setLaborCost(existingProduct.getLaborCost());
        existingProduct.setMarkupRate(existingProduct.getMarkupRate());
        existingProduct.setOrderDetails(existingProduct.getOrderDetails());
        existingProduct.setProductDetails(existingProduct.getProductDetails());
        existingProduct.setReceipt(existingProduct.getReceipt());
        existingProduct.setSellingPrice(existingProduct.getSellingPrice());
        existingProduct.setWarrantyPeriod(existingProduct.getWarrantyPeriod());
        return productService.save(existingProduct);
    }
}
