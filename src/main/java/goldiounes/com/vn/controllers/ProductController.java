package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.ProductDetailService;
import goldiounes.com.vn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-management")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @PostMapping("/products")
    public ResponseEntity<ResponseWrapper<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product created successfully", createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/products/category/{keyword}")
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getProductsByKeyword(@PathVariable String keyword) {
        List<ProductDTO> products = productService.findByCategory(keyword);
        ResponseWrapper<List<ProductDTO>> response = new ResponseWrapper<>("Products retrieved successfully", products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        ResponseWrapper<List<ProductDTO>> response = new ResponseWrapper<>("Products retrieved successfully", products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ResponseWrapper<ProductDTO>> getProduct(@PathVariable int id) {
        ProductDTO product = productService.getProduct(id);
        if (product != null) {
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product retrieved successfully", product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Product deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Product not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ResponseWrapper<ProductDTO>> updateProduct(@PathVariable int id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct != null) {
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product updated successfully", updatedProduct);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("products/{id}/productdetails")
    public ResponseEntity<ResponseWrapper<List<ProductDetailDTO>>> getAllProductDetails(@PathVariable int id) {
        List<ProductDetailDTO> productDetails = productDetailService.findAllByProductId(id);
        ResponseWrapper<List<ProductDetailDTO>> response = new ResponseWrapper<>("Product details retrieved successfully", productDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("productdetails/min/{id}")
    public ResponseEntity<ResponseWrapper<ProductDetailDTO>> getProductDetail(@PathVariable int id) {
        ProductDetailDTO productDetailDTO = productDetailService.getMinProductDetailByProductId(id);
        if (productDetailDTO != null) {
            ResponseWrapper<ProductDetailDTO> response = new ResponseWrapper<>("Product detail retrieved successfully", productDetailDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            ResponseWrapper<ProductDetailDTO> response = new ResponseWrapper<>("Product detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/productdetails/{id}")
    public ResponseEntity<ResponseWrapper<ProductDetailDTO>> getProductDetailById(@PathVariable int id) {
        ProductDetailDTO productDetail = productDetailService.findById(id);
        if (productDetail != null) {
            ResponseWrapper<ProductDetailDTO> response = new ResponseWrapper<>("Product detail retrieved successfully", productDetail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<ProductDetailDTO> response = new ResponseWrapper<>("Product detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/productdetails")
    public ResponseEntity<ResponseWrapper<ProductDetailDTO>> createProductDetail(@RequestBody ProductDetailDTO productDetailDTO) {
        ProductDetailDTO createdProductDetail = productDetailService.createProductDetail(productDetailDTO);
        ResponseWrapper<ProductDetailDTO> response = new ResponseWrapper<>("Product detail created successfully", createdProductDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/productdetails/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteProductDetail(@PathVariable int id) {
        boolean isDeleted = productDetailService.deleteById(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Product detail deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Product detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
