package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import goldiounes.com.vn.repositories.ProductRepo;
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
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDetailDTO> findAll() {
        List<ProductDetail> productDetails = productDetailRepo.findAll();
        if (productDetails.isEmpty()) {
            throw new RuntimeException("No products found");
        } else {
            return modelMapper.map(productDetails, new TypeToken<List<ProductDetailDTO>>() {}.getType());
        }
    }

    public ProductDetailDTO findById(int id) {
        ProductDetail existingProductDetail = productDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No ProductDetail found"));
        return modelMapper.map(existingProductDetail, new TypeToken<ProductDetailDTO>() {
        }.getType());
    }

    public ProductDetailDTO createProductDetail(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);
        Product existingProduct = productRepo.findById(productDetail.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("No Product found"));
        productDetail.setProduct(existingProduct);
        productDetailRepo.save(productDetail);
        return modelMapper.map(productDetail, new TypeToken<ProductDetailDTO>() {}.getType());
    }

    public void deleteById( int id){
        ProductDetail existingProductDetail = productDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No productdetail found"));
        productDetailRepo.deleteById(existingProductDetail.getProductDetailID());
    }
}