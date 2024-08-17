package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.models.entities.Setting;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import goldiounes.com.vn.repositories.SettingRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SettingRepo settingRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDetailDTO> findAllByProductId(int productId) {
        List<ProductDetail> productDetails = productDetailRepo.findByProductId(productId);
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
        Setting existingSetting = settingRepo.findById(productDetail.getSetting().getSettingID())
                .orElseThrow(() -> new RuntimeException("No Setting found"));
        productDetail.setSetting(existingSetting);
        productDetailRepo.save(productDetail);
        return modelMapper.map(productDetail, new TypeToken<ProductDetailDTO>() {}.getType());
    }

    public boolean deleteById(int id){
        ProductDetail existingProductDetail = productDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No productdetail found"));
        productDetailRepo.deleteById(existingProductDetail.getProductDetailID());
        return true;
    }

    public ProductDetailDTO getProductDetailBySize(Integer size, ProductDTO productDTO){
        Product  product = modelMapper.map(productDTO, Product.class);
        ProductDetail existingProductDetail = productDetailRepo.findBySizeAndProductId(size, product.getProductID());
        if (existingProductDetail == null) {
            throw new RuntimeException("No ProductDetail found");
        }
        return modelMapper.map(existingProductDetail, new TypeToken<ProductDetailDTO>() {}.getType());
    }

    public ProductDetailDTO updateProduct(int productDetailID, ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);
        ProductDetail productDetailUpdate = productDetailRepo.findById(productDetailID)
                .orElseThrow(() -> new RuntimeException("No ProductDetail found"));
        Product product = productRepo.findById(productDetail.getProductDetailID())
                .orElseThrow(() -> new RuntimeException("No Product found"));
        productDetail.setProduct(product);
        productDetailUpdate.setInventory(productDetail.getInventory());
        productDetailUpdate.setSize(productDetail.getSize());
        productDetailUpdate.setLaborCost(productDetail.getLaborCost());
        productDetailRepo.save(productDetailUpdate);
        return modelMapper.map(productDetailUpdate, new TypeToken<ProductDetailDTO>() {}.getType());
    }
}