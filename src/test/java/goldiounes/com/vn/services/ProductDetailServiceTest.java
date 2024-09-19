package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.ProductDetailDTO;
import goldiounes.com.vn.models.dtos.SettingDTO;
import goldiounes.com.vn.models.entities.*;
import goldiounes.com.vn.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductDetailServiceTest {
    private ProductDetailService productDetailService;
    private ProductDetailRepo productDetailRepo;
    private ProductRepo productRepo;
    private SettingRepo settingRepo;
    private DiamondRepo diamondRepo;
    private DiamondDetailRepo diamondDetailRepo;
    private DiamondDetailService diamondDetailService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        productDetailRepo = mock(ProductDetailRepo.class);
        productRepo = mock(ProductRepo.class);
        settingRepo = mock(SettingRepo.class);
        diamondRepo = mock(DiamondRepo.class);
        diamondDetailRepo = mock(DiamondDetailRepo.class);
        diamondDetailService = mock(DiamondDetailService.class);
        modelMapper = new ModelMapper();

        productDetailService = new ProductDetailService(productDetailRepo, productRepo, settingRepo, diamondRepo, diamondDetailRepo, diamondDetailService, modelMapper);
    }

    @Test
    void findAllByProductId_Success() {
        int productId = 1;
        List<ProductDetail> mockProductDetails = Arrays.asList(new ProductDetail(), new ProductDetail());
        when(productDetailRepo.findByProductId(productId)).thenReturn(mockProductDetails);

        List<ProductDetailDTO> result = productDetailService.findAllByProductId(productId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productDetailRepo).findByProductId(productId);
    }

    @Test
    void findAllByProductId_EmptyList() {
        int productId = 1;
        when(productDetailRepo.findByProductId(productId)).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> productDetailService.findAllByProductId(productId));
        verify(productDetailRepo).findByProductId(productId);
    }

    @Test
    void checkProductDetail_True() {
        int productId = 1;
        when(productDetailRepo.findByProductId(productId)).thenReturn(List.of(new ProductDetail()));

        boolean result = productDetailService.checkProductDetail(productId);

        assertTrue(result);
        verify(productDetailRepo).findByProductId(productId);
    }

    @Test
    void checkProductDetail_False() {
        int productId = 1;
        when(productDetailRepo.findByProductId(productId)).thenReturn(List.of());

        boolean result = productDetailService.checkProductDetail(productId);

        assertFalse(result);
        verify(productDetailRepo).findByProductId(productId);
    }

    @Test
    void findById_Success() {
        int id = 1;
        ProductDetail mockProductDetail = new ProductDetail();
        when(productDetailRepo.findById(id)).thenReturn(Optional.of(mockProductDetail));

        ProductDetailDTO result = productDetailService.findById(id);

        assertNotNull(result);
        verify(productDetailRepo).findById(id);
    }

    @Test
    void findById_NotFound() {
        int id = 1;
        when(productDetailRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productDetailService.findById(id));
        verify(productDetailRepo).findById(id);
    }

//    @Test
//    void createProductDetail_NewDetail() {
//        // Mock dữ liệu cho ProductDetailDTO
//        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
//        productDetailDTO.setSize(10);
//        productDetailDTO.setInventory(100);
//        productDetailDTO.setLaborCost(50);
//        productDetailDTO.setMarkupRate(1.2);
//
//        // Mock Product và Setting
//        Product mockProduct = new Product();
//        mockProduct.setProductID(1);
//        Setting mockSetting = new Setting();
//        mockSetting.setSettingID(1);
//        mockSetting.setPrice(100); // Price của setting
//
//        // Create and set ProductDTO
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setProductId(mockProduct.getProductID());
//        productDetailDTO.setProduct(productDTO);
//
//        SettingDTO settingDTO = new SettingDTO();
//        settingDTO.setSettingI(mockSetting.getSettingID());
//        productDetailDTO.setSetting(settingDTO);
//
//        // Set Product và Setting IDs in DTO
//        productDetailDTO.setProduct(mockProduct.getProductID());
//        productDetailDTO.setSetting(mockSetting.getSettingID());
//
//        // Mock Diamond và DiamondDetail
//        Diamond mockDiamond = new Diamond();
//        mockDiamond.setDiamondID(1);
//        mockDiamond.setPrice(200);
//        DiamondDetail mockDiamondDetail = new DiamondDetail();
//        mockDiamondDetail.setDiamond(mockDiamond);
//        mockDiamondDetail.setQuantity(2); // Số lượng kim cương
//
//        List<DiamondDetail> diamondDetails = new ArrayList<>();
//        diamondDetails.add(mockDiamondDetail);
//
//        ProductDetail productDetail = new ProductDetail();
//        productDetail.setDiamondDetails(diamondDetails);
//
//        when(productRepo.findById(anyInt())).thenReturn(Optional.of(mockProduct));
//        when(settingRepo.findById(anyInt())).thenReturn(Optional.of(mockSetting));
//        when(productDetailRepo.findBySizeAndProductId(anyInt(), anyInt())).thenReturn(Optional.empty()); // Corrected
//        when(diamondRepo.findById(anyInt())).thenReturn(Optional.of(mockDiamond));
//        when(diamondDetailRepo.save(any(DiamondDetail.class))).thenAnswer(i -> i.getArguments()[0]);
//        when(productDetailRepo.save(any(ProductDetail.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        // Mock modelMapper behavior
//        when(modelMapper.map(any(ProductDetailDTO.class), eq(ProductDetail.class))).thenReturn(productDetail);
//        when(modelMapper.map(any(ProductDetail.class), eq(ProductDetailDTO.class))).thenReturn(productDetailDTO);
//
//        ProductDetailDTO result = productDetailService.createProductDetail(productDetailDTO);
//
//        assertNotNull(result);
//        double expectedTotalCost = (200 * 2) + 100 + 50; // Tổng giá
//        double expectedSellingPrice = expectedTotalCost * 1.2; // Giá bán với markup
//        assertEquals(expectedSellingPrice, result.getSellingPrice(), 0.01); // Allow small delta for floating point comparison
//        verify(productDetailRepo).save(any(ProductDetail.class));
//    }



    @Test
    void deleteById_Success() {
        int id = 1;
        ProductDetail mockProductDetail = new ProductDetail();
        mockProductDetail.setDiamondDetails(List.of());
        when(productDetailRepo.findById(id)).thenReturn(Optional.of(mockProductDetail));

        boolean result = productDetailService.deleteById(id);

        assertTrue(result);
        verify(productDetailRepo).deleteById(id);
    }

    @Test
    void deleteById_NotFound() {
        int id = 1;
        when(productDetailRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productDetailService.deleteById(id));
        verify(productDetailRepo, never()).deleteById(anyInt());
    }

    @Test
    void getProductDetailBySize_Success() {
        Integer size = 10;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);

        ProductDetail mockProductDetail = new ProductDetail();
        when(productDetailRepo.findBySizeAndProductId(size, 1)).thenReturn(mockProductDetail);

        ProductDetailDTO result = productDetailService.getProductDetailBySize(size, productDTO);

        assertNotNull(result);
        verify(productDetailRepo).findBySizeAndProductId(size, 1);
    }

    @Test
    void getProductDetailBySize_NotFound() {
        Integer size = 10;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(1);

        when(productDetailRepo.findBySizeAndProductId(size, 1)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> productDetailService.getProductDetailBySize(size, productDTO));
        verify(productDetailRepo).findBySizeAndProductId(size, 1);
    }

//    @Test
//    void updateProduct_Success() {
//        int productDetailID = 1;
//        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
//        productDetailDTO.setInventory(100);
//        productDetailDTO.setSize(10);
//        productDetailDTO.setLaborCost(50);
//        productDetailDTO.setMarkupRate(1.2);
//
//        ProductDetail existingProductDetail = new ProductDetail();
//        existingProductDetail.setProductDetailID(productDetailID);
//
//        Product mockProduct = new Product();
//        mockProduct.setProductID(1);
//
//        Setting mockSetting = new Setting();
//        mockSetting.setSettingID(1);
//        mockSetting.setPrice(100);
//
//        when(productDetailRepo.findById(productDetailID)).thenReturn(Optional.of(existingProductDetail));
//        when(productRepo.findById(anyInt())).thenReturn(Optional.of(mockProduct));
//        when(settingRepo.findById(anyInt())).thenReturn(Optional.of(mockSetting));
//        when(productDetailRepo.save(any(ProductDetail.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        ProductDetailDTO result = productDetailService.updateProduct(productDetailID, productDetailDTO);
//
//        assertNotNull(result);
//        assertEquals(100, result.getInventory());
//        assertEquals(10, result.getSize());
//
//        // Giả sử chi phí kim cương và các thành phần khác không thay đổi
//        double expectedTotalCost = 50 + 100; // Update cost (có thể thay đổi tuỳ thuộc vào logic)
//        double expectedSellingPrice = expectedTotalCost * 1.2;
//        assertEquals(expectedSellingPrice, result.getSellingPrice());
//        verify(productDetailRepo).save(any(ProductDetail.class));
//    }


    @Test
    void getMinProductDetailByProductId_Success() {
        int productID = 1;
        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setSellingPrice(100.0);
        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setSellingPrice(80.0);

        when(productDetailRepo.findByProductId(productID)).thenReturn(Arrays.asList(productDetail1, productDetail2));

        ProductDetailDTO result = productDetailService.getMinProductDetailByProductId(productID);

        assertNotNull(result);
        assertEquals(80.0, result.getSellingPrice());
        verify(productDetailRepo).findByProductId(productID);
    }

    @Test
    void getMinProductDetailByProductId_EmptyList() {
        int productID = 1;
        when(productDetailRepo.findByProductId(productID)).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> productDetailService.getMinProductDetailByProductId(productID));
        verify(productDetailRepo).findByProductId(productID);
    }
}