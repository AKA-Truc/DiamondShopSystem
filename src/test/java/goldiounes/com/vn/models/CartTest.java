package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.CartItem;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartTest {

    @Test
    void testGetterAndSetter() {
        User user = new User();
        Cart cart = new Cart(user);

        List<Order> orders = Collections.emptyList();
        List<CartItem> cartItems = Collections.emptyList();

        cart.setUser(user);
        cart.setOrders(orders);
        cart.setCartItems(cartItems);

        assertEquals(user, cart.getUser());
        assertEquals(orders, cart.getOrders());
        assertEquals(cartItems, cart.getCartItems());

        cart.setUser(null);
        cart.setOrders(null);
        cart.setCartItems(null);

        assertNull(cart.getUser());
        assertNull(cart.getOrders());
        assertNull(cart.getCartItems());
    }

    @Test
    void testConstructor() {
        User user = new User();
        Cart cart = new Cart(user);

        assertNotNull(cart);
        assertEquals(user, cart.getUser());
        assertNull(cart.getOrders());
        assertNull(cart.getCartItems());
    }

    @Test
    void testDefaultConstructor() {
        Cart cart = new Cart();

        assertNotNull(cart);
        assertNull(cart.getUser());
        assertNull(cart.getOrders());
        assertNull(cart.getCartItems());
    }
}
