package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.ProductDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.dtos.WarrantyDTO;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.models.entities.Warranty;
import goldiounes.com.vn.repositories.OrderRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import goldiounes.com.vn.repositories.UserRepo;
import goldiounes.com.vn.repositories.WarrantyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WarrantyServiceTest {

    private WarrantyService warrantyService;
    private WarrantyRepo warrantyRepo;
    private ProductRepo productRepo;
    private UserRepo userRepo;
    private OrderRepo orderRepo;
    private ModelMapper modelMapper;

    private Warranty warranty;
    private WarrantyDTO warrantyDTO;
    private User user;
    private UserDTO userDTO;
    private Product product;
    private ProductDTO productDTO;
    private Order order;

    @BeforeEach
    public void setUp() {
        warrantyRepo = Mockito.mock(WarrantyRepo.class);
        productRepo = Mockito.mock(ProductRepo.class);
        userRepo = Mockito.mock(UserRepo.class);
        orderRepo = Mockito.mock(OrderRepo.class);
        modelMapper = new ModelMapper();
        warrantyService = new WarrantyService(warrantyRepo, productRepo, userRepo, orderRepo, modelMapper);

        user = new User();
        user.setUserID(1);
        user.setEmail("test@example.com");

        userDTO  = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setEmail("test@example.com");

        product = new Product();
        product.setProductID(1);
        product.setWarrantyPeriod(12); // 12 months

        productDTO = new ProductDTO();
        productDTO.setProductId(1);
        productDTO.setWarrantyPeriod(12);

        order = new Order();
        order.setOrderID(1);
        order.setStartDate(new Date());

        warranty = new Warranty();
        warranty.setWarrantyID(1);
        warranty.setUser(user);
        warranty.setProduct(product);
        warranty.setEndDate(new Date());

        warrantyDTO = new WarrantyDTO();
        warrantyDTO.setUser(userDTO);
        warrantyDTO.setProduct(productDTO);
    }

    @Test
    public void testGetAllWarranties() {
        List<Warranty> warranties = List.of(warranty);
        when(warrantyRepo.findAll()).thenReturn(warranties);

        List<WarrantyDTO> result = warrantyService.getAllWarranties();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(warrantyRepo, times(1)).findAll();
    }

    @Test
    public void testGetWarrantyById_WarrantyExists() {
        when(warrantyRepo.findById(1)).thenReturn(Optional.of(warranty));

        WarrantyDTO result = warrantyService.getWarranty(1);

        assertNotNull(result);
        verify(warrantyRepo, times(1)).findById(1);
    }

    @Test
    public void testGetWarrantyById_WarrantyNotFound() {
        when(warrantyRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> warrantyService.getWarranty(1));
    }

    @Test
    public void testFindByUserEmail_UserExists() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(user);
        when(warrantyRepo.findByUserID(1)).thenReturn(List.of(warranty));

        List<WarrantyDTO> result = warrantyService.findByUserEmail("test@example.com");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepo, times(1)).findByEmail("test@example.com");
        verify(warrantyRepo, times(1)).findByUserID(1);
    }

    @Test
    public void testFindByUserEmail_UserNotFound() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> warrantyService.findByUserEmail("test@example.com"));
    }

//    @Test
//    public void testCreateWarranty_OrderProductUserExists() {
//        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
//        when(productRepo.findById(1)).thenReturn(Optional.of(product));
//        when(userRepo.findById(1)).thenReturn(Optional.of(user));
//        when(warrantyRepo.save(any(Warranty.class))).thenReturn(warranty);
//
//        WarrantyDTO result = warrantyService.createWarranty(1, warrantyDTO);
//
//        assertNotNull(result);
//        verify(orderRepo, times(1)).findById(1);
//        verify(productRepo, times(1)).findById(1);
//        verify(userRepo, times(1)).findById(1);
//        verify(warrantyRepo, times(1)).save(any(Warranty.class));
//    }

    @Test
    public void testCreateWarranty_ProductNotFound() {
        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
        when(productRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> warrantyService.createWarranty(1, warrantyDTO));
    }

//    @Test
//    public void testUpdateWarranty_WarrantyExists() {
//        when(warrantyRepo.findById(1)).thenReturn(Optional.of(warranty));
//        when(warrantyRepo.save(any(Warranty.class))).thenReturn(warranty);
//
//        WarrantyDTO result = warrantyService.updateWarranty(1, warrantyDTO);
//
//        assertNotNull(result);
//        verify(warrantyRepo, times(1)).findById(1);
//        verify(warrantyRepo, times(1)).save(any(Warranty.class));
//    }

    @Test
    public void testUpdateWarranty_WarrantyNotFound() {
        when(warrantyRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> warrantyService.updateWarranty(1, warrantyDTO));
    }

    @Test
    public void testDeleteWarranty_WarrantyExists() {
        when(warrantyRepo.existsById(1)).thenReturn(true);

        boolean result = warrantyService.deleteWarranty(1);

        assertTrue(result);
        verify(warrantyRepo, times(1)).existsById(1);
        verify(warrantyRepo, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteWarranty_WarrantyNotFound() {
        when(warrantyRepo.existsById(1)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> warrantyService.deleteWarranty(1));
    }
}
