package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entity.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void testGetterAndSetters() {
        Category category = new Category("Nhẫn");

        assertEquals("Nhẫn",category.getCategoryName());

        category.setCategoryName("Lắc");

        assertEquals("Lắc",category.getCategoryName());
    }

    @Test
    void testConstructor(){
        Category category = new Category("Nhẫn");

        assertNotNull(category);
        assertEquals("Nhẫn",category.getCategoryName());
    }

    @Test
    void testDefaultConstructor(){
        Category category = new Category();

        assertNotNull(category);
        assertNull(category.getCategoryName());
    }
}
