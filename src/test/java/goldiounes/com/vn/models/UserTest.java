package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testGetterAndSetters() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer","female", "active");

        assertEquals("John", user.getUserName());
        assertEquals("12345", user.getPassword());
        assertEquals("abc@gmail.com", user.getEmail());
        assertEquals("New York", user.getAddress());
        assertEquals("Customer", user.getRole());
        assertEquals("female", user.getGender());

        user.setUserName("Lisa");
        user.setPassword("54321");
        user.setEmail("Lisa@gmail.com");
        user.setAddress("Paris");
        user.setRole("Sale Staff");
        user.setGender("Male");

        assertEquals("Lisa", user.getUserName());
        assertEquals("54321", user.getPassword());
        assertEquals("Lisa@gmail.com", user.getEmail());
        assertEquals("Paris", user.getAddress());
        assertEquals("Sale Staff", user.getRole());
        assertEquals("Male", user.getGender());

    }

    void testConstructor() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer", "Female", "active");

        assertNotNull(user);

        assertEquals("John", user.getUserName());
        assertEquals("12345", user.getPassword());
        assertEquals("abc@gmail.com", user.getEmail());
        assertEquals("New York", user.getAddress());
        assertEquals("Customer", user.getRole());
        assertEquals("Female", user.getGender());
    }

    void testDefaultConstructor() {
        User user = new User();

        assertNotNull(user);
        assertNull(user.getUserName());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getAddress());
        assertNull(user.getRole());
        assertNull(user.getGender());
    }
}
