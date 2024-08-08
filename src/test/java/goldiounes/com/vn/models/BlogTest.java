package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entity.Blog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlogTest {
    @Test
    void testGetterAndSetter() {
        Blog blog = new Blog("Java","Web");

        assertEquals("Javascript", blog.getTitle());
        assertEquals("Laptrinhweb", blog.getContent());

        blog.setTitle("Javasrc");
        assertEquals("Javasrc", blog.getTitle());
        blog.setContent("Laptrinh");
        assertEquals("Laptrinh", blog.getContent());
    }

    @Test
    void testConstructor() {
        Blog blog = new Blog("Script","Lapweb");
        assertNotNull(blog);
        assertEquals("Script", blog.getTitle());
        assertEquals("Lapweb", blog.getContent());
    }

    @Test
    void testDefaultConstructor() {
        Blog blog = new Blog();
        assertNotNull(blog);
        assertEquals("", blog.getTitle());
        assertEquals("", blog.getContent());
    }
}