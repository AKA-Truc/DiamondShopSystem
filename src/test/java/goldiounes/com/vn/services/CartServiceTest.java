package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CartDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepo cartRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCart_ExistingCart_ReturnsCartDTO() {
        // Arrange
        int cartId = 1;
        Cart cart = new Cart();
        cart.setCartID(cartId);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cartId);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(modelMapper.map(cart, new TypeToken<CartDTO>(){}.getType())).thenReturn(cartDTO);

        // Act
        CartDTO result = cartService.getCart(cartId);

        // Assert
        assertNotNull(result);
        assertEquals(cartId, result.getCartId());
        verify(cartRepo).findById(cartId);
        verify(modelMapper).map(cart, new TypeToken<CartDTO>(){}.getType());
    }

    @Test
    void getCart_NonExistingCart_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        when(cartRepo.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.getCart(cartId));
        verify(cartRepo).findById(cartId);
    }

    @Test
    void createCart_ValidUserDTO_ReturnsCartDTO() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        User user = new User();
        user.setUserID(1);
        Cart cart = new Cart();
        cart.setCartID(1);
        cart.setUser(user);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);

        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepo.findById(user.getUserID())).thenReturn(Optional.of(user));
        when(cartRepo.save(any(Cart.class))).thenReturn(cart);
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

        // Act
        CartDTO result = cartService.createCart(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCartId());
        verify(userRepo).findById(user.getUserID());
        verify(cartRepo).save(any(Cart.class));
        verify(modelMapper).map(cart, CartDTO.class);
    }

    @Test
    void deleteCart_ExistingCart_DeletesCart() {
        // Arrange
        int cartId = 1;
        Cart cart = new Cart();
        cart.setCartID(cartId);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));

        // Act
        cartService.deleteCart(cartId);

        // Assert
        verify(cartRepo).findById(cartId);
        verify(cartRepo).delete(cart);
    }

    @Test
    void deleteCart_NonExistingCart_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        when(cartRepo.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.deleteCart(cartId));
        verify(cartRepo).findById(cartId);
        verify(cartRepo, never()).delete(any(Cart.class));
    }

    @Test
    void updateCart_ExistingCart_ReturnsUpdatedCartDTO() {
        // Arrange
        int cartId = 1;
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cartId);
        Cart cart = new Cart();
        cart.setCartID(cartId);
        User user = new User();
        user.setUserID(1);
        cart.setUser(user);

        when(modelMapper.map(cartDTO, Cart.class)).thenReturn(cart);
        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepo.save(cart)).thenReturn(cart);
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

        // Act
        CartDTO result = cartService.updateCart(cartId, cartDTO);

        // Assert
        assertNotNull(result);
        assertEquals(cartId, result.getCartId());
        verify(cartRepo).findById(cartId);
        verify(cartRepo).save(cart);
        verify(modelMapper).map(cart, CartDTO.class);
    }

    @Test
    void getAllCarts_CartsExist_ReturnsListOfCartDTOs() {
        // Arrange
        List<Cart> carts = Arrays.asList(new Cart(), new Cart());
        List<CartDTO> cartDTOs = Arrays.asList(new CartDTO(), new CartDTO());

        when(cartRepo.findAll()).thenReturn(carts);
        when(modelMapper.map(carts, new TypeToken<List<CartDTO>>(){}.getType())).thenReturn(cartDTOs);

        // Act
        List<CartDTO> result = cartService.getAllCarts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cartRepo).findAll();
        verify(modelMapper).map(carts, new TypeToken<List<CartDTO>>(){}.getType());
    }

    @Test
    void getAllCarts_NoCartsExist_ThrowsRuntimeException() {
        // Arrange
        when(cartRepo.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.getAllCarts());
        verify(cartRepo).findAll();
    }

    @Test
    void getCartByUser_ExistingUserAndCart_ReturnsCartDTO() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setUserID(userId);
        Cart cart = new Cart();
        cart.setCartID(1);
        cart.setUser(user);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(1);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepo.findCartByUserId(userId)).thenReturn(cart);
        when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

        // Act
        CartDTO result = cartService.getCartbyUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCartId());
        verify(userRepo).findById(userId);
        verify(cartRepo).findCartByUserId(userId);
        verify(modelMapper).map(cart, CartDTO.class);
    }

    @Test
    void getCartByUser_NonExistingUser_ThrowsRuntimeException() {
        // Arrange
        int userId = 1;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.getCartbyUser(userId));
        verify(userRepo).findById(userId);
        verify(cartRepo, never()).findCartByUserId(anyInt());
    }

    @Test
    void getCartByUser_ExistingUserNoCart_ThrowsRuntimeException() {
        // Arrange
        int userId = 1;
        User user = new User();
        user.setUserID(userId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(cartRepo.findCartByUserId(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartService.getCartbyUser(userId));
        verify(userRepo).findById(userId);
        verify(cartRepo).findCartByUserId(userId);
    }
}