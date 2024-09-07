package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.*;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailTest {
    @Test
    void testGetterAndSetters() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer", "Female", "active", "url");
        Cart cart = new Cart(user);
        Promotion promotion = new Promotion("Summer Sale", "Discount on all summer items", new Date(), new Date(), 20);
        String shippingAddress = "123 Main St";
        int totalPrice = 100;
        String status = "Pending";

        Date startDate = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1
        String typePayment = "Banking";

        Order order = new Order(user, cart, promotion, shippingAddress, startDate, typePayment);

        order.setTotalPrice(totalPrice);
        order.setStatus(status);

        Category category = new Category("AAA");

        Product product = new Product(category,"BBB","URL","SubURL",24,"active");

        OrderDetail orderDetail = new OrderDetail(order, product, 20, 15);

        assertEquals(order, orderDetail.getOrder());
        assertEquals(product, orderDetail.getProduct());
        assertEquals(20, orderDetail.getQuantity());
    }

    @Test
    void testConstructor() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer", "Female", "active", "url");
        Cart cart = new Cart(user);
        Promotion promotion = new Promotion("Summer Sale", "Discount on all summer items", new Date(), new Date(), 20);
        String shippingAddress = "123 Main St";
        int totalPrice = 100;
        String status = "Pending";
        Date startDate = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1
        String typePayment = "Banking";

        Order order = new Order(user, cart, promotion, shippingAddress, startDate, typePayment);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);

        Category category = new Category("AAA");

        Product product = new Product(category,"BBB","URL","SubURL",24,"active");

        OrderDetail orderDetail = new OrderDetail(order, product, 20, 15);

        assertNotNull(orderDetail);

        assertEquals(order, orderDetail.getOrder());
        assertEquals(product, orderDetail.getProduct());
        assertEquals(20, orderDetail.getQuantity());
    }

    @Test
    void testDefaultConstructor() {
        OrderDetail orderDetail = new OrderDetail();

        assertNotNull(orderDetail);

        assertNull(orderDetail.getOrder());
        assertNull(orderDetail.getProduct());
        assertEquals(0, orderDetail.getQuantity());
    }
}
