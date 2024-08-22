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
    private DiamondRepo diamondRepo;

    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    @Autowired
    private DiamondDetailService diamondDetailService;

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

    @Transactional
    public ProductDetailDTO createProductDetail(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);

        // Fetch and set the existing Product
        Product existingProduct = productRepo.findById(productDetail.getProduct().getProductID())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productDetail.getProduct().getProductID()));
        productDetail.setProduct(existingProduct);

        // Fetch and set the existing Setting
        Setting existingSetting = settingRepo.findById(productDetail.getSetting().getSettingID())
                .orElseThrow(() -> new EntityNotFoundException("Setting not found with ID: " + productDetail.getSetting().getSettingID()));
        productDetail.setSetting(existingSetting);

        // Check if ProductDetail already exists
        ProductDetail existingProductDetail = productDetailRepo.findBySizeAndProductId(productDetail.getSize(), existingProduct.getProductID());
        if (existingProductDetail != null) {
            existingProductDetail.setInventory(existingProductDetail.getInventory() + productDetail.getInventory());
            ProductDetail updatedProductDetail = productDetailRepo.save(existingProductDetail);
            return modelMapper.map(updatedProductDetail, ProductDetailDTO.class);
        }

        productDetailRepo.save(productDetail);

        // Process DiamondDetails and calculate price
        List<DiamondDetail> newDiamondDetails = new ArrayList<>();
        double totalCost = 0;

        for (DiamondDetail diamondDetail : productDetail.getDiamondDetails()) {
            diamondDetail.setProductDetail(productDetail);
            Diamond diamond = diamondRepo.findById(diamondDetail.getDiamond().getDiamondID())
                    .orElseThrow(() -> new EntityNotFoundException("Diamond not found with ID: " + diamondDetail.getDiamond().getDiamondID()));
            diamondDetail.setDiamond(diamond);

            DiamondDetail createdDiamondDetail = diamondDetailRepo.save(diamondDetail);
            newDiamondDetails.add(createdDiamondDetail);
            totalCost += diamond.getPrice() * createdDiamondDetail.getQuantity();
        }

        productDetail.setDiamondDetails(newDiamondDetails);

        // Add setting price and labor cost to total cost
        totalCost += existingSetting.getPrice() + productDetail.getLaborCost();

        // Calculate and set selling price
        productDetail.setSellingPrice(totalCost * productDetail.getMarkupRate());

        // Save and return the new ProductDetail
        ProductDetail savedProductDetail = productDetailRepo.save(productDetail);
        return modelMapper.map(savedProductDetail, new TypeToken<ProductDetailDTO>() {}.getType());
    }

    public boolean deleteById(int id){
        ProductDetail existingProductDetail = productDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No productdetail found"));
        for (DiamondDetail diamondDetail : existingProductDetail.getDiamondDetails()){
            diamondDetailService.deleteById(diamondDetail.getDiamond().getDiamondID());
        }
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
        productDetailUpdate.setMarkupRate(productDetail.getMarkupRate());
        productDetailUpdate.setSellingPrice(productDetail.getLaborCost() * productDetail.getMarkupRate());
        productDetailRepo.save(productDetailUpdate);
        return modelMapper.map(productDetailUpdate, new TypeToken<ProductDetailDTO>() {}.getType());
    }

    public ProductDetailDTO getMinProductDetailByProductId(int productID) {
        List<ProductDetail> productDetails = productDetailRepo.findByProductId(productID);
        if (productDetails.isEmpty()) {
            throw new RuntimeException("No ProductDetail found");
        }

        int MinID = 0;
        double MinPrice = productDetails.get(0).getSellingPrice();

        for (ProductDetail productDetail1 : productDetails) {
            if(MinPrice >= productDetail1.getSellingPrice()) {
                MinID = productDetail1.getProductDetailID();
                MinPrice = productDetail1.getSellingPrice();
            }
        }

        ProductDetail returnProductDetail = productDetailRepo.findById(MinID)
                .orElseThrow(() -> new RuntimeException("No ProductDetail found"));

        return modelMapper.map(returnProductDetail, new TypeToken<ProductDetailDTO>() {}.getType());
    }
}