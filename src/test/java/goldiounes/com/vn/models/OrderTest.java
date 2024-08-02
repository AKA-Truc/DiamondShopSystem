package goldiounes.com.vn.models;

import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    void testGetterAndSetters() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer");
        Cart cart = new Cart(user, "NEW");
        Order order = new Order();
    }
}
