package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Cart;
import goldiounes.com.vn.models.CartItem;
import goldiounes.com.vn.services.CartService;
import goldiounes.com.vn.services.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private CartItemService cartItemService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Cart tests
    @Test
    public void testCreateCart() {
        Cart cart = new Cart();
        when(cartService.save(cart)).thenReturn(cart);
        assertNotNull(cartController.createCart(cart));
        verify(cartService).save(cart);
    }

    @Test
    public void testGetCartById() {
        Cart cart = new Cart();
        when(cartService.findById(1)).thenReturn(cart);
        assertNotNull(cartController.getCartById(1));
        verify(cartService).findById(1);
    }

    @Test
    public void testGetAllCarts() {
        when(cartService.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), cartController.getAllCarts());
        verify(cartService).findAll();
    }

    @Test
    public void testUpdateCart() {
        Cart cart = new Cart();
        when(cartService.findById(1)).thenReturn(cart);
        when(cartService.save(cart)).thenReturn(cart);
        assertNotNull(cartController.updateCart(1, cart));
        verify(cartService).findById(1);
        verify(cartService).save(cart);
    }

    @Test
    public void testDeleteCart() {
        doNothing().when(cartService).deleteById(1);
        cartController.deleteCart(1);
        verify(cartService).deleteById(1);
    }

    // CartItem tests
    @Test
    public void testCreateCartItem() {
        CartItem cartItem = new CartItem();
        when(cartItemService.save(cartItem)).thenReturn(cartItem);
        assertNotNull(cartController.createCartItem(cartItem));
        verify(cartItemService).save(cartItem);
    }

    @Test
    public void testGetAllCartItems() {
        when(cartItemService.findAll()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), cartController.getAllCartItems());
        verify(cartItemService).findAll();
    }

    @Test
    public void testUpdateCartItem() {
        CartItem cartItem = new CartItem();
        when(cartItemService.findById(1)).thenReturn(cartItem);
        when(cartItemService.save(cartItem)).thenReturn(cartItem);
        assertNotNull(cartController.updateCartItem(1, cartItem));
        verify(cartItemService).findById(1);
        verify(cartItemService).save(cartItem);
    }

    @Test
    public void testDeleteCartItem() {
        doNothing().when(cartItemService).deleteById(1);
        cartController.deleteCartItem(1);
        verify(cartItemService).deleteById(1);
    }
}
