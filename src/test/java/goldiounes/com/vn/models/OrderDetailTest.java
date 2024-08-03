package goldiounes.com.vn.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailTest {
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

        Category category = new Category("AAA");

        Product product = new Product(category,"BBB","URL",
                1.2,500,24,10);

        OrderDetail orderDetail = new OrderDetail(order, product, 20, 30);

        assertEquals(order, orderDetail.getOrder());
        assertEquals(product, orderDetail.getProduct());
        assertEquals(20, orderDetail.getQuantity());
        assertEquals(30, orderDetail.getPrice());
    }

    @Test
    void testConstructor() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer");
        Cart cart = new Cart(user);
        Promotion promotion = new Promotion("Summer Sale", "Discount on all summer items", new Date(), new Date(), 20);
        String shippingAddress = "123 Main St";
        int totalPrice = 100;
        String status = "Pending";

        Order order = new Order(user, cart, promotion, shippingAddress);
        order.setTotalPrice(totalPrice);
        order.setStatus(status);

        Category category = new Category("AAA");

        Product product = new Product(category,"BBB","URL",
                1.2,500,24,10);

        OrderDetail orderDetail = new OrderDetail(order, product, 20, 30);

        assertNotNull(orderDetail);

        assertEquals(order, orderDetail.getOrder());
        assertEquals(product, orderDetail.getProduct());
        assertEquals(20, orderDetail.getQuantity());
        assertEquals(30, orderDetail.getPrice());
    }

    @Test
    void testDefaultConstructor() {
        OrderDetail orderDetail = new OrderDetail();

        assertNotNull(orderDetail);

        assertNull(orderDetail.getOrder());
        assertNull(orderDetail.getProduct());
        assertEquals(0, orderDetail.getQuantity());
        assertEquals(0, orderDetail.getPrice());
    }
}