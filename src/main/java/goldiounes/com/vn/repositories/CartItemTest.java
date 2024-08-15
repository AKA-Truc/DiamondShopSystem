package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entity.CartItem;
import goldiounes.com.vn.models.entity.Cart;
import goldiounes.com.vn.models.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {

    @Test
    void testGetterAndSetter() {
        Cart cart = new Cart();
        Product product = new Product();
        CartItem cartItem = new CartItem(cart, product, 2);

        assertEquals(cart, cartItem.getCart());
        assertEquals(product, cartItem.getProduct());
        assertEquals(2, cartItem.getQuantity());

        Cart newCart = new Cart();
        Product newProduct = new Product();
        cartItem.setCart(newCart);
        cartItem.setProduct(newProduct);
        cartItem.setQuantity(5);

        assertEquals(newCart, cartItem.getCart());
        assertEquals(newProduct, cartItem.getProduct());
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    void testConstructor() {
        Cart cart = new Cart();
        Product product = new Product();
        CartItem cartItem = new CartItem(cart, product, 3);

        assertNotNull(cartItem);
        assertEquals(cart, cartItem.getCart());
        assertEquals(product, cartItem.getProduct());
        assertEquals(3, cartItem.getQuantity());
    }

    @Test
    void testDefaultConstructor() {
        CartItem cartItem = new CartItem();

        assertNotNull(cartItem);
        assertNull(cartItem.getCart());
        assertNull(cartItem.getProduct());
        assertEquals(0, cartItem.getQuantity());
    }
}