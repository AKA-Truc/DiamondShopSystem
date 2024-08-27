package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductDetailServiceTest {
    private ProductDetailService productDetailService;
    private ProductDetailRepo productDetailRepo;
    private ProductRepo productRepo;
    private SettingRepo settingRepo;
    private DiamondRepo diamondRepo;
    private DiamondDetailRepo diamondDetailRepo;
    private DiamondDetailService diamondDetailService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        productDetailRepo = Mockito.mock(ProductDetailRepo.class);
        productRepo = Mockito.mock(ProductRepo.class);
        settingRepo = Mockito.mock(SettingRepo.class);
        diamondRepo = Mockito.mock(DiamondRepo.class);
        diamondDetailRepo = Mockito.mock(DiamondDetailRepo.class);
        diamondDetailService = Mockito.mock(DiamondDetailService.class);
        modelMapper = new ModelMapper();

        productDetailService = new ProductDetailService(productDetailRepo, productRepo, settingRepo, diamondRepo, diamondDetailRepo, diamondDetailService, modelMapper);
    }

//    @Test
//    void testCreateProductDetail_NewDetail() {
//        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
//        productDetailDTO.setSize(10);
//        productDetailDTO.setInventory(100);
//        productDetailDTO.setLaborCost(50);
//        productDetailDTO.setMarkupRate(1.2);
//
//        ProductDetail productDetail = new ProductDetail();
//        productDetail.setSize(10);
//        productDetail.setInventory(100);
//
//        Product product = new Product();
//        product.setProductID(1);
//        productDetail.setProduct(product);
//
//        Setting setting = new Setting();
//        setting.setSettingID(1);
//        productDetail.setSetting(setting);
//
//        when(productRepo.findById(1)).thenReturn(java.util.Optional.of(product));
//        when(settingRepo.findById(1)).thenReturn(java.util.Optional.of(setting));
//        when(productDetailRepo.findBySizeAndProductId(10, 1)).thenReturn(null);
//        when(productDetailRepo.save(any(ProductDetail.class))).thenReturn(productDetail);
//
//        ProductDetailDTO result = productDetailService.createProductDetail(productDetailDTO);
//
//        assertNotNull(result);
//        assertEquals(100 * 1.2 + 50, result.getSellingPrice());
//    }

    @Test
    void testGetMinProductDetailByProductId_Success() {
        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setProductDetailID(1);
        productDetail1.setSellingPrice(100.0);

        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setProductDetailID(2);
        productDetail2.setSellingPrice(80.0);

        when(productDetailRepo.findByProductId(1)).thenReturn(List.of(productDetail1, productDetail2));

        ProductDetailDTO result = productDetailService.getMinProductDetailByProductId(1);

        assertNotNull(result);
        assertEquals(80.0, result.getSellingPrice());
        assertEquals(2, result.getProductDetailId());
    }

    @Test
    void testGetMinProductDetailByProductId_NotFound() {
        when(productDetailRepo.findByProductId(1)).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productDetailService.getMinProductDetailByProductId(1));
        assertEquals("No ProductDetail found for the given product ID", exception.getMessage());
    }
}
