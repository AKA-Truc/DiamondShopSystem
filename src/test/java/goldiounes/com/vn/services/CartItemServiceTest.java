package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CartItemDTO;
import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.CartItem;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.repositories.CartItemRepo;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    @Mock
    private CartItemRepo cartItemRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCartItemById_ExistingCartItem_ReturnsCartItemDTO() {
        // Arrange
        int cartItemId = 1;
        CartItem cartItem = new CartItem();
        cartItem.setCartItemID(cartItemId);
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(cartItemId);

        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        when(modelMapper.map(cartItem, CartItemDTO.class)).thenReturn(cartItemDTO);

        // Act
        CartItemDTO result = cartItemService.GetCartItemById(cartItemId);

        // Assert
        assertNotNull(result);
        assertEquals(cartItemId, result.getCartItemId());
        verify(cartItemRepo).findById(cartItemId);
        verify(modelMapper).map(cartItem, CartItemDTO.class);
    }

    @Test
    void getCartItemById_NonExistingCartItem_ThrowsRuntimeException() {
        // Arrange
        int cartItemId = 1;
        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.GetCartItemById(cartItemId));
        verify(cartItemRepo).findById(cartItemId);
    }

    @Test
    void getAllCartItems_ExistingCartWithItems_ReturnsListOfCartItemDTOs() {
        // Arrange
        int userId = 1;
        Cart cart = new Cart();
        cart.setCartID(1);
        List<CartItem> cartItems = Arrays.asList(new CartItem(), new CartItem());
        List<CartItemDTO> cartItemDTOs = Arrays.asList(new CartItemDTO(), new CartItemDTO());

        when(cartRepo.findCartByUserId(userId)).thenReturn(cart);
        when(cartItemRepo.findByCartID(cart.getCartID())).thenReturn(cartItems);
        when(modelMapper.map(cartItems, new TypeToken<List<CartItemDTO>>() {
        }.getType())).thenReturn(cartItemDTOs);

        // Act
        List<CartItemDTO> result = cartItemService.getAllCartItems(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cartRepo).findCartByUserId(userId);
        verify(cartItemRepo).findByCartID(cart.getCartID());
        verify(modelMapper).map(cartItems, new TypeToken<List<CartItemDTO>>() {
        }.getType());
    }

    @Test
    void getAllCartItems_NoCartFound_ThrowsRuntimeException() {
        // Arrange
        int userId = 1;
        when(cartRepo.findCartByUserId(userId)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.getAllCartItems(userId));
        verify(cartRepo).findCartByUserId(userId);
    }

    @Test
    void addItem_NewItem_AddsNewCartItem() {
        // Arrange
        int cartId = 1;
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setQuantity(2);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2);
        Cart cart = new Cart();
        cart.setCartID(cartId);
        Product product = new Product();
        product.setProductID(1);
        cartItem.setProduct(product);

        when(modelMapper.map(cartItemDTO, CartItem.class)).thenReturn(cartItem);
        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepo.findById(product.getProductID())).thenReturn(Optional.of(product));
        when(cartItemRepo.findByCartID(cartId)).thenReturn(Collections.emptyList());
        when(cartItemRepo.save(any(CartItem.class))).thenReturn(cartItem);
        when(modelMapper.map(any(CartItem.class), eq(CartItemDTO.class))).thenReturn(cartItemDTO);

        // Act
        CartItemDTO result = cartItemService.addItem(cartItemDTO, cartId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getQuantity());
        verify(cartRepo).findById(cartId);
        verify(productRepo).findById(product.getProductID());
        verify(cartItemRepo).findByCartID(cartId);
        verify(cartItemRepo).save(any(CartItem.class));
    }

    @Test
    void addItem_ExistingItem_UpdatesQuantity() {
        // Arrange
        int cartId = 1;
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setQuantity(2);
        CartItem existingCartItem = new CartItem();
        existingCartItem.setQuantity(3);
        Cart cart = new Cart();
        cart.setCartID(cartId);
        Product product = new Product();
        product.setProductID(1);
        existingCartItem.setProduct(product);

        when(modelMapper.map(cartItemDTO, CartItem.class)).thenReturn(existingCartItem);
        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepo.findById(product.getProductID())).thenReturn(Optional.of(product));
        when(cartItemRepo.findByCartID(cartId)).thenReturn(Collections.singletonList(existingCartItem));
        when(cartItemRepo.save(existingCartItem)).thenReturn(existingCartItem);
        when(modelMapper.map(existingCartItem, CartItemDTO.class)).thenReturn(cartItemDTO);

        // Act
        CartItemDTO result = cartItemService.addItem(cartItemDTO, cartId);

        // Assert
        assertNotNull(result);
        assertEquals(5, existingCartItem.getQuantity());  // 3 (existing) + 2 (new)
        verify(cartRepo).findById(cartId);
        verify(productRepo).findById(product.getProductID());
        verify(cartItemRepo).findByCartID(cartId);
        verify(cartItemRepo).save(existingCartItem);
    }

    @Test
    void removeCartItem_ExistingCartItem_RemovesItem() {
        // Arrange
        int cartItemId = 1;
        CartItem cartItem = new CartItem();
        cartItem.setCartItemID(cartItemId);

        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(cartItem));

        // Act
        boolean result = cartItemService.removeCartItem(cartItemId);

        // Assert
        assertTrue(result);
        verify(cartItemRepo).findById(cartItemId);
        verify(cartItemRepo).delete(cartItem);
    }

    @Test
    void removeCartItem_NonExistingCartItem_ThrowsRuntimeException() {
        // Arrange
        int cartItemId = 1;
        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.removeCartItem(cartItemId));
        verify(cartItemRepo).findById(cartItemId);
        verify(cartItemRepo, never()).delete(any(CartItem.class));
    }

    @Test
    void removeAllCartItems_ExistingCart_RemovesAllItems() {
        // Arrange
        int cartId = 1;
        Cart cart = new Cart();
        cart.setCartID(cartId);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));

        // Act
        boolean result = cartItemService.removeAllCartItems(cartId);

        // Assert
        assertTrue(result);
        verify(cartRepo).findById(cartId);
        verify(cartItemRepo).deleteByCartID(cartId);
    }

    @Test
    void removeAllCartItems_NonExistingCart_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        when(cartRepo.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.removeAllCartItems(cartId));
        verify(cartRepo).findById(cartId);
        verify(cartItemRepo, never()).deleteByCartID(anyInt());
    }

    @Test
    void findByCarId_ExistingCartWithItems_ReturnsListOfCartItemDTOs() {
        // Arrange
        int cartId = 1;
        Cart cart = new Cart();
        cart.setCartID(cartId);
        List<CartItem> cartItems = Arrays.asList(new CartItem(), new CartItem());
        List<CartItemDTO> cartItemDTOs = Arrays.asList(new CartItemDTO(), new CartItemDTO());

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepo.findByCartID(cartId)).thenReturn(cartItems);
        when(modelMapper.map(cartItems, new TypeToken<List<CartItemDTO>>() {
        }.getType())).thenReturn(cartItemDTOs);

        // Act
        List<CartItemDTO> result = cartItemService.findByCarId(cartId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cartRepo).findById(cartId);
        verify(cartItemRepo).findByCartID(cartId);
        verify(modelMapper).map(cartItems, new TypeToken<List<CartItemDTO>>() {
        }.getType());
    }

    @Test
    void findByCarId_ExistingCartNoItems_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        Cart cart = new Cart();
        cart.setCartID(cartId);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartItemRepo.findByCartID(cartId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.findByCarId(cartId));
        verify(cartRepo).findById(cartId);
        verify(cartItemRepo).findByCartID(cartId);
    }

    @Test
    void updateQuantity_ExistingCartItem_UpdatesQuantity() {
        // Arrange
        int cartItemId = 1;
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setQuantity(5);
        CartItem existingCartItem = new CartItem();
        existingCartItem.setCartItemID(cartItemId);

        when(modelMapper.map(cartItemDTO, CartItem.class)).thenReturn(existingCartItem);
        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(existingCartItem));
        when(cartItemRepo.save(existingCartItem)).thenReturn(existingCartItem);
        when(modelMapper.map(existingCartItem, new TypeToken<CartItemDTO>() {
        }.getType())).thenReturn(cartItemDTO);

        // Act
        CartItemDTO result = cartItemService.updateQuantity(cartItemId, cartItemDTO);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(cartItemRepo).findById(cartItemId);
        verify(cartItemRepo).save(existingCartItem);
        verify(modelMapper).map(existingCartItem, new TypeToken<CartItemDTO>() {
        }.getType());
    }

    @Test
    void updateQuantity_NonExistingCartItem_ThrowsRuntimeException() {
        // Arrange
        int cartItemId = 1;
        CartItemDTO cartItemDTO = new CartItemDTO();
        when(cartItemRepo.findById(cartItemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.updateQuantity(cartItemId, cartItemDTO));
        verify(cartItemRepo).findById(cartItemId);
        verify(cartItemRepo, never()).save(any(CartItem.class));
    }

    @Test
    void findByProductId_ExistingProductWithCartItems_ReturnsListOfCartItemDTOs() {
        // Arrange
        int productId = 1;
        List<CartItem> cartItems = Arrays.asList(new CartItem(), new CartItem());
        List<CartItemDTO> cartItemDTOs = Arrays.asList(new CartItemDTO(), new CartItemDTO());

        when(cartItemRepo.findByProductID(productId)).thenReturn(cartItems);
        when(modelMapper.map(cartItems, new TypeToken<List<CartItemDTO>>() {
        }.getType())).thenReturn(cartItemDTOs);

        // Act
        List<CartItemDTO> result = cartItemService.findByProductId(productId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cartItemRepo).findByProductID(productId);
        verify(modelMapper).map(cartItems, new TypeToken<List<CartItemDTO>>() {
        }.getType());
    }

    @Test
    void findByProductId_NoCartItemsFound_ThrowsRuntimeException() {
        // Arrange
        int productId = 1;
        when(cartItemRepo.findByProductID(productId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.findByProductId(productId));
        verify(cartItemRepo).findByProductID(productId);
    }

    @Test
    void deleteById_ExistingCartItem_DeletesCartItem() {
        // Arrange
        int cartItemId = 1;
        when(cartItemRepo.existsById(cartItemId)).thenReturn(true);

        // Act
        boolean result = cartItemService.deleteByID(cartItemId);

        // Assert
        assertTrue(result);
        verify(cartItemRepo).existsById(cartItemId);
        verify(cartItemRepo).deleteById(cartItemId);
    }

    @Test
    void deleteById_NonExistingCartItem_ThrowsRuntimeException() {
        // Arrange
        int cartItemId = 1;
        when(cartItemRepo.existsById(cartItemId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.deleteByID(cartItemId));
        verify(cartItemRepo).existsById(cartItemId);
        verify(cartItemRepo, never()).deleteById(cartItemId);
    }

    @Test
    void addItem_CartNotFound_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        CartItemDTO cartItemDTO = new CartItemDTO();
        when(cartRepo.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.addItem(cartItemDTO, cartId));
        verify(cartRepo).findById(cartId);
        verify(productRepo, never()).findById(anyInt());
    }

    @Test
    void addItem_ProductNotFound_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        CartItemDTO cartItemDTO = new CartItemDTO();
        Cart cart = new Cart();
        cart.setCartID(cartId);
        CartItem cartItem = new CartItem();
        Product product = new Product();
        product.setProductID(1);
        cartItem.setProduct(product);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(modelMapper.map(cartItemDTO, CartItem.class)).thenReturn(cartItem);
        when(productRepo.findById(product.getProductID())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.addItem(cartItemDTO, cartId));
        verify(cartRepo).findById(cartId);
        verify(productRepo).findById(product.getProductID());
        verify(cartItemRepo, never()).findByCartID(anyInt());
    }

    @Test
    void getAllCartItems_EmptyCartItems_ReturnsEmptyList() {
        // Arrange
        int userId = 1;
        Cart cart = new Cart();
        cart.setCartID(1);

        when(cartRepo.findCartByUserId(userId)).thenReturn(cart);
        when(cartItemRepo.findByCartID(cart.getCartID())).thenReturn(Collections.emptyList());
        when(modelMapper.map(Collections.emptyList(), new TypeToken<List<CartItemDTO>>(){}.getType())).thenReturn(Collections.emptyList());

        // Act
        List<CartItemDTO> result = cartItemService.getAllCartItems(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartRepo).findCartByUserId(userId);
        verify(cartItemRepo).findByCartID(cart.getCartID());
        verify(modelMapper).map(Collections.emptyList(), new TypeToken<List<CartItemDTO>>(){}.getType());
    }

    @Test
    void findByCarId_NonExistingCart_ThrowsRuntimeException() {
        // Arrange
        int cartId = 1;
        when(cartRepo.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> cartItemService.findByCarId(cartId));
        verify(cartRepo).findById(cartId);
        verify(cartItemRepo, never()).findByCartID(anyInt());
    }

}