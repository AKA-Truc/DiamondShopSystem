package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SettingRepo settingRepo;

    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    @Autowired
    private DiamondDetailService diamondDetailService;

    @Autowired
    private ModelMapper modelMapper;

    public ProductDetailService(ProductDetailRepo productDetailRepo, ProductRepo productRepo, SettingRepo settingRepo, DiamondRepo diamondRepo, DiamondDetailRepo diamondDetailRepo, DiamondDetailService diamondDetailService, ModelMapper modelMapper) {
        this.productDetailRepo = productDetailRepo;
        this.productRepo = productRepo;
        this.settingRepo = settingRepo;
        this.diamondRepo = diamondRepo;
        this.diamondDetailRepo = diamondDetailRepo;
        this.diamondDetailService = diamondDetailService;
        this.modelMapper = modelMapper;
    }

    public List<ProductDetailDTO> findAllByProductId(int productId) {
        List<ProductDetail> productDetails = productDetailRepo.findByProductId(productId);
        List<ProductDetail> active = new ArrayList<>();
        if (productDetails.isEmpty()) {
            throw new RuntimeException("No ProductDetail found for the given product ID");
        }
        for (ProductDetail product : productDetails) {
            if(product.getStatus().equals("active")){
                active.add(product);
            }
        }
        return modelMapper.map(active, new TypeToken<List<ProductDetailDTO>>() {}.getType());
    }

    public boolean checkProductDetail(int productId) {
        return !productDetailRepo.findByProductId(productId).isEmpty();
    }

    public ProductDetailDTO findById(int id) {
        ProductDetail existingProductDetail = productDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No ProductDetail found with ID: " + id));
        if(existingProductDetail.getStatus().equals("inactive")){
            throw new RuntimeException("ProductDetail is already inactive");
        }
        return modelMapper.map(existingProductDetail, ProductDetailDTO.class);
    }

    @Transactional
    public ProductDetailDTO createProductDetail(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);

        Product existingProduct = productRepo.findById(productDetail.getProduct().getProductID())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productDetail.getProduct().getProductID()));
        productDetail.setProduct(existingProduct);

        Setting existingSetting = settingRepo.findById(productDetail.getSetting().getSettingID())
                .orElseThrow(() -> new EntityNotFoundException("Setting not found with ID: " + productDetail.getSetting().getSettingID()));
        productDetail.setSetting(existingSetting);

        ProductDetail existingProductDetail = productDetailRepo.findBySizeAndProductId(productDetail.getSize(), existingProduct.getProductID());
        if (existingProductDetail != null) {
            existingProductDetail.setInventory(existingProductDetail.getInventory() + productDetail.getInventory());
            return modelMapper.map(productDetailRepo.save(existingProductDetail), ProductDetailDTO.class);
        }
        productDetail.setStatus("active");
        productDetailRepo.save(productDetail);

        List<DiamondDetail> newDiamondDetails = new ArrayList<>();
        double totalCost = 0;

        for (DiamondDetail diamondDetail : productDetail.getDiamondDetails()) {
            diamondDetail.setProductDetail(productDetail);
            Diamond diamond = diamondRepo.findById(diamondDetail.getDiamond().getDiamondID())
                    .orElseThrow(() -> new EntityNotFoundException("Diamond not found with ID: " + diamondDetail.getDiamond().getDiamondID()));
            diamondDetail.setDiamond(diamond);

            newDiamondDetails.add(diamondDetailRepo.save(diamondDetail));
            totalCost += diamond.getPrice() * diamondDetail.getQuantity();
        }

        productDetail.setDiamondDetails(newDiamondDetails);
        totalCost += existingSetting.getPrice() + productDetail.getLaborCost();
        productDetail.setSellingPrice(totalCost * productDetail.getMarkupRate());

        return modelMapper.map(productDetailRepo.save(productDetail), ProductDetailDTO.class);
    }

    public boolean deleteById(int id) {
        ProductDetail existingProductDetail = productDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No ProductDetail found with ID: " + id));
        existingProductDetail.setStatus("inactive");
        productDetailRepo.save(existingProductDetail);
        return true;
    }

    public ProductDetailDTO getProductDetailBySize(Integer size, ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        ProductDetail existingProductDetail = productDetailRepo.findBySizeAndProductId(size, product.getProductID());
        if (existingProductDetail == null) {
            throw new RuntimeException("No ProductDetail found with size: " + size + " and productID: " + product.getProductID());
        }
        return modelMapper.map(existingProductDetail, ProductDetailDTO.class);
    }

    public ProductDetailDTO updateProduct(int productDetailID, ProductDetailDTO productDetailDTO) {
        ProductDetail productDetailUpdate = productDetailRepo.findById(productDetailID)
                .orElseThrow(() -> new RuntimeException("No ProductDetail found with ID: " + productDetailID));

        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);
        Product product = productRepo.findById(productDetail.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("No Product found with ID: " + productDetail.getProduct().getProductID()));


        productDetailUpdate.setProduct(product);
        productDetailUpdate.setInventory(productDetail.getInventory());
        productDetailUpdate.setSize(productDetail.getSize());
        productDetailUpdate.setLaborCost(productDetail.getLaborCost());
        productDetailUpdate.setMarkupRate(productDetail.getMarkupRate());
        productDetailUpdate.setSellingPrice(productDetail.getLaborCost() * productDetail.getMarkupRate());
        productDetailUpdate.setStatus("active");
        return modelMapper.map(productDetailRepo.save(productDetailUpdate), ProductDetailDTO.class);
    }

    public ProductDetailDTO getMinProductDetailByProductId(int productID) {
        List<ProductDetail> productDetails = productDetailRepo.findByProductId(productID);
        if (productDetails.isEmpty()) {
            throw new RuntimeException("No ProductDetail found for the given product ID");
        }

        ProductDetail minProductDetail = productDetails.stream()
                .min(Comparator.comparingDouble(ProductDetail::getSellingPrice))
                .filter(p -> p.getProduct().getStatus().equals("active"))
                .orElseThrow(() -> new RuntimeException("No ProductDetail found with minimum selling price"));

        return modelMapper.map(minProductDetail, ProductDetailDTO.class);
    }
}
