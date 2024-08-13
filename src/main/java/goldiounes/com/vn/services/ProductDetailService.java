package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDetailDTO> findAll() {
        List<ProductDetail> productDetails = productDetailRepo.findAll();
        if (productDetails.isEmpty()) {
            throw new RuntimeException("No products found");
        } else {
            return modelMapper.map(productDetails, new TypeToken<List<ProductDetailDTO>>() {
            }.getType());
        }
    }

    public ProductDetailDTO findById(int id) {
        ProductDetail existingProductDetail = productDetailRepo.findById(id).orElseThrow(() -> new RuntimeException("No productdetail found"));
        return modelMapper.map(existingProductDetail, new TypeToken<ProductDetailDTO>() {
        }.getType());
    }

    public ProductDetailDTO createProductDetail(ProductDetail productDetail) {
        Optional<ProductDetail> existingProduct;
        existingProduct = productDetailRepo.findById(productDetail.getProductDetailID());
        if (existingProduct.isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        else {
            return modelMapper.map(existingProduct, new TypeToken<List<ProductDetailDTO>>() {
            }.getType());
        }
    }

    public void deleteById( int id){
        ProductDetail existingProductDetail = productDetailRepo.findById(id).orElseThrow(() -> new RuntimeException("No productdetail found"));
        productDetailRepo.deleteById(id);
    }
}