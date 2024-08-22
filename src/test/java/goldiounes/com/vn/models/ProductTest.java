package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.models.entities.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ProductTest {

    @Test
    void testDefaultConstructor() {
        Product product = new Product();

        assertNotNull(product);
        assertNull(product.getCategory());
        assertNull(product.getProductName());
        assertNull(product.getImageURL());
        assertNull(product.getSubImageURL());
        assertEquals(0.0, product.getWarrantyPeriod());
    }

    @Test
    void testConstructor(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL","SubURL",24,"active");

        assertNotNull(product);
    }

    @Test
    void testGettersAndSetters() {
        Category category = new Category("AAA");

        Product product = new Product(category,"BBB","URL","SubURL",24,"active");

        assertNotNull(product.getCategory());
        assertEquals("AAA",product.getCategory().getCategoryName());
        assertEquals("BBB",product.getProductName());
        assertEquals("URL",product.getImageURL());
        assertEquals("SubURL",product.getSubImageURL());
        assertEquals(24,product.getWarrantyPeriod());;

        Category category1 = new Category("aaa");
        product.setCategory(category1);
        product.setProductName("bbb");
        product.setImageURL("url");
        product.setSubImageURL("subUrl");
        product.setWarrantyPeriod(240);

        assertNotNull(product.getCategory());
        assertEquals("aaa",product.getCategory().getCategoryName());
        assertEquals("bbb",product.getProductName());
        assertEquals("url",product.getImageURL());
        assertEquals("subUrl",product.getSubImageURL());
        assertEquals(240,product.getWarrantyPeriod());
    }
}
