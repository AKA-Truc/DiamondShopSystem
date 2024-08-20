package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.Order;
import goldiounes.com.vn.models.entities.Promotion;
import goldiounes.com.vn.models.entities.User;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        Date startDate = new GregorianCalendar(2024, Calendar.MAY, 1).getTime(); //2024/5/1

        Order order = new Order(user, cart, promotion, shippingAddress, startDate);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);

        assertEquals(user, order.getUser());
        assertEquals(cart, order.getCart());
        assertEquals(promotion, order.getPromotion());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(startDate, order.getStartDate()); // Kiểm tra startDate
        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(status, order.getStatus());

        User newUser = new User("Lisa", "54321", "Lisa@gmail.com", "Paris", "Sale Staff");
        Cart newCart = new Cart();
        Promotion newPromotion = new Promotion("Winter Sale", "Discount on all winter items", new Date(), new Date(), 30);
        String newShippingAddress = "456 Elm St";
        int newTotalPrice = 200;
        String newStatus = "Shipped";
        Date newStartDate = new GregorianCalendar(2024, Calendar.NOVEMBER, 15).getTime(); //2024/11/15

        order.setUser(newUser);
        order.setCart(newCart);
        order.setPromotion(newPromotion);
        order.setShippingAddress(newShippingAddress);
        order.setTotalPrice(newTotalPrice);
        order.setStatus(newStatus);
        order.setStartDate(newStartDate); // Đặt giá trị mới cho startDate

        assertEquals(newUser, order.getUser());
        assertEquals(newCart, order.getCart());
        assertEquals(newPromotion, order.getPromotion());
        assertEquals(newShippingAddress, order.getShippingAddress());
        assertEquals(newTotalPrice, order.getTotalPrice());
        assertEquals(newStatus, order.getStatus());
        assertEquals(newStartDate, order.getStartDate());
    }

    @Test
    void testConstructor() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer");
        Cart cart = new Cart();
        Promotion promotion = new Promotion("Summer Sale", "Discount on all summer items", new Date(), new Date(), 20);
        String shippingAddress = "123 Main St";

        Date startDate = new GregorianCalendar(2024, Calendar.MAY, 1).getTime(); //2024/5/1
        Order order = new Order(user, cart, promotion, shippingAddress, startDate);

        assertNotNull(order);

        assertEquals(user, order.getUser());
        assertEquals(cart, order.getCart());
        assertEquals(promotion, order.getPromotion());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(startDate, order.getStartDate());
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
        assertNull(order.getStartDate());
    }
}
