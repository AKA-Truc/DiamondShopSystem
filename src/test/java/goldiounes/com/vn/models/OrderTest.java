package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.Promotion;
import goldiounes.com.vn.models.entities.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void testGetterAndSetters() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer");
        Cart cart = new Cart(user);
        Promotion promotion = new Promotion("Summer Sale", "Discount on all summer items", new Date(), new Date(), 20);
        String shippingAddress = "123 Main St";
        int totalPrice = 100;
        String status = "Pending";

        Order order = new Order(user, cart, promotion, shippingAddress);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);

        assertEquals(user, order.getUser());
        assertEquals(cart, order.getCart());
        assertEquals(promotion, order.getPromotion());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(status, order.getStatus());

        User newUser = new User("Lisa", "54321", "Lisa@gmail.com", "Paris", "Sale Staff");
        Cart newCart = new Cart();
        Promotion newPromotion = new Promotion("Winter Sale", "Discount on all winter items", new Date(), new Date(), 30);
        String newShippingAddress = "456 Elm St";
        int newTotalPrice = 200;
        String newStatus = "Shipped";

        order.setUser(newUser);
        order.setCart(newCart);
        order.setPromotion(newPromotion);
        order.setShippingAddress(newShippingAddress);
        order.setTotalPrice(newTotalPrice);
        order.setStatus(newStatus);

        assertEquals(newUser, order.getUser());
        assertEquals(newCart, order.getCart());
        assertEquals(newPromotion, order.getPromotion());
        assertEquals(newShippingAddress, order.getShippingAddress());
        assertEquals(newTotalPrice, order.getTotalPrice());
        assertEquals(newStatus, order.getStatus());
    }

    @Test
    void testConstructor() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer");
        Cart cart = new Cart();
        Promotion promotion = new Promotion("Summer Sale", "Discount on all summer items", new Date(), new Date(), 20);
        String shippingAddress = "123 Main St";

        Order order = new Order(user, cart, promotion, shippingAddress);

        assertNotNull(order);

        assertEquals(user, order.getUser());
        assertEquals(cart, order.getCart());
        assertEquals(promotion, order.getPromotion());
        assertEquals(shippingAddress, order.getShippingAddress());
    }

    @Test
    void testDefaultConstructor() {
        Order order = new Order();

        assertNotNull(order);
        assertNull(order.getUser());
        assertNull(order.getCart());
        assertNull(order.getPromotion());
        assertNull(order.getShippingAddress());
        assertEquals(0, order.getTotalPrice());
        assertNull(order.getStatus());
        assertNull(order.getOrderDetails());
    }


}
