package goldiounes.com.vn.models;

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
        assertEquals(0.0, product.getMarkupRate());
        assertEquals(0.0, product.getLaborCost());
        assertEquals(0.0, product.getSellingPrice());
        assertEquals(0.0, product.getWarrantyPeriod());
        assertEquals(0, product.getInventory());
        assertNull(product.getProductDetails());
        assertNull(product.getCartItems());
        assertNull(product.getOrderDetails());
    }

    @Test
    void testConstructor(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL",
                1.2,500,600,24,10);

        assertNotNull(product);
    }

    @Test
    void testGettersAndSetters() {
        Category category = new Category("AAA");

        Product product = new Product(category,"BBB","URL",
                1.2,500,600,24,10);


        assertNotNull(product.getCategory());
        assertEquals("AAA",product.getCategory().getCategoryName());
        assertEquals("BBB",product.getProductName());
        assertEquals("URL",product.getImageURL());
        assertEquals(1.2,product.getMarkupRate());
        assertEquals(500,product.getLaborCost());
        assertEquals(600,product.getSellingPrice());
        assertEquals(24,product.getWarrantyPeriod());
        assertEquals(10, product.getInventory());

        Category category1 = new Category("aaa");
        product.setCategory(category1);
        product.setProductName("bbb");
        product.setImageURL("url");
        product.setMarkupRate(2.1);
        product.setLaborCost(5000);
        product.setSellingPrice(6000);
        product.setWarrantyPeriod(240);
        product.setInventory(1);

        assertNotNull(product.getCategory());
        assertEquals("aaa",product.getCategory().getCategoryName());
        assertEquals("bbb",product.getProductName());
        assertEquals("url",product.getImageURL());
        assertEquals(2.1,product.getMarkupRate());
        assertEquals(5000,product.getLaborCost());
        assertEquals(6000,product.getSellingPrice());
        assertEquals(240,product.getWarrantyPeriod());
        assertEquals(1, product.getInventory());
    }
}