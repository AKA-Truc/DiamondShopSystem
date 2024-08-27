package goldiounes.com.vn.services;


import goldiounes.com.vn.models.dtos.CategoryDTO;
import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.repositories.CategoryRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private ProductService productService;
    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;
    private FileUploadService fileUploadService;
    private ProductDetailService productDetailService;
    private OrderDetailService orderDetailService;

    private Product product;
    private ProductDTO productDTO;
    private Category category;
    private CategoryDTO categoryDTO;
    private MultipartFile imageFile;
    private MultipartFile subImageFile;

    @BeforeEach
    public void setUp() {
        productRepo = Mockito.mock(ProductRepo.class);
        categoryRepo = Mockito.mock(CategoryRepo.class);
        modelMapper = new ModelMapper();
        fileUploadService = Mockito.mock(FileUploadService.class);
        productDetailService = Mockito.mock(ProductDetailService.class);
        orderDetailService = Mockito.mock(OrderDetailService.class);

        productService = new ProductService(productRepo,categoryRepo, modelMapper, fileUploadService, productDetailService, orderDetailService);

        category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Electronics");

        product = new Product();
        product.setProductID(1);
        product.setProductName("Smartphone");
        product.setStatus("active");
        product.setCategory(category);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1);
        categoryDTO.setCategoryName("Electronics");

        productDTO = new ProductDTO();
        productDTO.setProductName("Smartphone");
        productDTO.setCategory(categoryDTO);
    }

    @Test
    public void testGetAllProducts_NoProductsFound() {
        when(productRepo.findAll()).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> productService.getAllProducts());
    }

    @Test
    public void testGetAllProducts_ActiveProductsFound() {
        when(productRepo.findAll()).thenReturn(List.of(product));

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Smartphone", result.get(0).getProductName());
    }

    @Test
    public void testGetProduct_ProductNotFound() {
        when(productRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProduct(1));
    }

    @Test
    public void testGetProduct_ProductFoundAndActive() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProduct(1);

        assertNotNull(result);
        assertEquals("Smartphone", result.getProductName());
    }

    @Test
    public void testCreateProduct_Success() throws IOException {
        when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(category));
        when(productRepo.save(any(Product.class))).thenReturn(product);
        when(fileUploadService.uploadImage(imageFile)).thenReturn("image-url");
        when(fileUploadService.uploadImage(subImageFile)).thenReturn("subimage-url");

        ProductDTO result = productService.createProduct(productDTO, imageFile, subImageFile);

        assertNotNull(result);
        assertEquals("Smartphone", result.getProductName());
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        when(productRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.deleteProduct(1));
    }

    @Test
    public void testDeleteProduct_Success() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(orderDetailService.findByProductId(1)).thenReturn(List.of());

        boolean result = productService.deleteProduct(1);

        assertTrue(result);
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    public void testFindByCategoryAndPrice_NoProductsFound() {
        when(productRepo.findByCategoryKeyWord("Electronics")).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> productService.findByCategoryAndPrice("Electronics", 100, 1000));
    }

    @Test
    public void testUpdateProduct_Success() throws IOException {
        when(productRepo.findById(anyInt())).thenReturn(Optional.of(product));
        when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(category));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.updateProduct(1, productDTO, imageFile, subImageFile);

        assertNotNull(result);
        assertEquals("Smartphone", result.getProductName());
        verify(productRepo, times(1)).save(any(Product.class));
    }
}

