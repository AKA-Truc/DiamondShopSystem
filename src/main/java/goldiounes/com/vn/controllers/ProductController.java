
package goldiounes.com.vn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.ProductDetailService;
import goldiounes.com.vn.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product-management")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/product")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<ProductDTO>> createProduct(
            @RequestParam("product") String productDTOJson,
            @RequestPart("imageURL") MultipartFile imageFile,
            @RequestPart("subImageURL") MultipartFile subImageURL) {

        try {
            ProductDTO productDTO = objectMapper.readValue(productDTOJson, ProductDTO.class);
            ProductDTO createdProduct = productService.createProduct(productDTO, imageFile, subImageURL);
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product created successfully", createdProduct);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Error creating product: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("products/category/{keyword}/{minPrice}/{maxPrice}")
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getProductsByKeyword(
            @PathVariable String keyword,
            @PathVariable double minPrice,
            @PathVariable double maxPrice
    ) {
        List<ProductDTO> products = productService.findByCategoryAndPrice(keyword, minPrice, maxPrice);
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF')")
    public ResponseEntity<ResponseWrapper<ProductDTO>> updateProduct(
            @PathVariable int id,
            @RequestParam("product") String productDTOJson,
            @RequestParam("imageURL") MultipartFile imageFile,
            @RequestParam("subImageURL") MultipartFile subImageURL) {

        try {
            ProductDTO productDTO = objectMapper.readValue(productDTOJson, ProductDTO.class);
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO, imageFile, subImageURL);
            if (updatedProduct != null) {
                ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product updated successfully", updatedProduct);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Product not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            ResponseWrapper<ProductDTO> response = new ResponseWrapper<>("Error updating product: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("products/{id}/productdetails")
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF', 'ROLE_DELIVERY_STAFF')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF')")
    public ResponseEntity<ResponseWrapper<ProductDetailDTO>> createProductDetail(@RequestBody ProductDetailDTO productDetailDTO) {
        ProductDetailDTO createdProductDetail = productDetailService.createProductDetail(productDetailDTO);
        ResponseWrapper<ProductDetailDTO> response = new ResponseWrapper<>("Product detail created successfully", createdProductDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/productdetails/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
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