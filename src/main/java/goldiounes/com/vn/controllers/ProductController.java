package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Category;
import goldiounes.com.vn.models.Product;
import goldiounes.com.vn.services.CategoryService;
import goldiounes.com.vn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product-management")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        List<Product> Products = productService.findByName(product.getProductName());
        if(Products.isEmpty()) {
            product.setSellingPrice(product.getMarkupRate()*product.getLaborCost());
            return productService.save(product);
        }
        throw new RuntimeException("Đã Tồn Tại");
    }

//    @GetMapping("/products/category/{keyword}")
//    public List<Product> getProductsByKeyword(@PathVariable String keyword) {
//        List<Category> categories = categoryService.findCategoryByKeyword(keyword);
//        List<Product> products = new ArrayList<>();
//        for(Category category : categories) {
//            List<Product> productsByCategory = productService.findByCategory(category.getCategoryID());
//            if(productsByCategory != null) {
//                products.addAll(productsByCategory);
//            }
//        }
//        if(products.isEmpty()) {
//            throw new RuntimeException("Product List is empty");
//        }
//        return products;
//    }

    @GetMapping("/products/category/{keyword}")
    public List<Product> getProductsByKeyword(@PathVariable String keyword) {
        List<Product> products = productService.findByCategory(keyword);
        if(products.isEmpty()) {
            throw new RuntimeException("Product List is empty");
        }
        return products;
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
        existingProduct.setWarranties(product.getWarranties());
        existingProduct.setImageURL(product.getImageURL());
        existingProduct.setInventory(product.getInventory());
        existingProduct.setLaborCost(product.getLaborCost());
        existingProduct.setMarkupRate(product.getMarkupRate());
        existingProduct.setProductDetails(product.getProductDetails());
        existingProduct.setReceipts(product.getReceipts());
        existingProduct.setSellingPrice(product.getMarkupRate()*product.getLaborCost());
        existingProduct.setWarrantyPeriod(product.getWarrantyPeriod());
        return productService.save(existingProduct);
    }
}