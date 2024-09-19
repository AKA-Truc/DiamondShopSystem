package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testGetterAndSetters() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer","female", "active", "url");

        assertEquals("John", user.getUserName());
        assertEquals("12345", user.getPassword());
        assertEquals("abc@gmail.com", user.getEmail());
        assertEquals("New York", user.getAddress());
        assertEquals("Customer", user.getRole());
        assertEquals("female", user.getGender());
        assertEquals("active", user.getStatus());
        assertEquals("url", user.getUrl());

        user.setUserName("Lisa");
        user.setPassword("54321");
        user.setEmail("Lisa@gmail.com");
        user.setAddress("Paris");
        user.setRole("Sale Staff");
        user.setGender("Male");
        user.setStatus("Active");
        user.setUrl("url");

        assertEquals("Lisa", user.getUserName());
        assertEquals("54321", user.getPassword());
        assertEquals("Lisa@gmail.com", user.getEmail());
        assertEquals("Paris", user.getAddress());
        assertEquals("Sale Staff", user.getRole());
        assertEquals("Male", user.getGender());
        assertEquals("Active", user.getStatus());
        assertEquals("url", user.getUrl());

    }

    void testConstructor() {
        User user = new User("John", "12345", "abc@gmail.com", "New York", "Customer", "Female", "active", "url");

        assertNotNull(user);

        assertEquals("John", user.getUserName());
        assertEquals("12345", user.getPassword());
        assertEquals("abc@gmail.com", user.getEmail());
        assertEquals("New York", user.getAddress());
        assertEquals("Customer", user.getRole());
        assertEquals("Female", user.getGender());
        assertEquals("active", user.getStatus());
        assertEquals("url", user.getUrl());
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
