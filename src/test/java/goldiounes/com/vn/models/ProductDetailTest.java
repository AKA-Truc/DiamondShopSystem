package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.models.entities.Setting;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

public class ProductDetailTest {

    @Test
    public void testDefaultConstructor() {
        ProductDetail productDetail = new ProductDetail();

        assertNotNull(productDetail);
        assertNull(productDetail.getProduct(), "Product should be null by default");
        assertNull(productDetail.getSetting(), "Setting should be null by default");
        assertEquals(0, productDetail.getLaborCost(), "LaborCost should be initialized to 0");
        assertEquals(0.0, productDetail.getMarkupRate(), "MarkupRate should be initialized to 0.0");
        assertNull(productDetail.getSize(), "Size should be null by default");
        assertEquals(0, productDetail.getInventory(), "Inventory should be initialized to 0");
        assertNull(productDetail.getDiamondDetails(), "DiamondDetails should be null by default");
        assertNull(productDetail.getStatus(), "Status should be null by default"); // Modify this if "status" is not assigned in the default constructor
    }

    @Test
    void testConstructor() {
        Product product = new Product();  // Ensure no-arg constructor exists
        Setting setting = new Setting();  // Ensure no-arg constructor exists

        ProductDetail productDetail = new ProductDetail(product, setting, 500, 1.25, 42, 100);

        assertNotNull(productDetail);
        assertEquals(product, productDetail.getProduct());
        assertEquals(setting, productDetail.getSetting());
        assertEquals(500, productDetail.getLaborCost());
        assertEquals(1.25, productDetail.getMarkupRate());
        assertEquals(42, productDetail.getSize());
        assertEquals(100, productDetail.getInventory());
        assertEquals("active", productDetail.getStatus());
        assertNull(productDetail.getDiamondDetails());
    }

    @Test
    void testGettersAndSetters() {
        Product product = new Product();
        Setting setting = new Setting();

        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setSetting(setting);
        productDetail.setLaborCost(1000);
        productDetail.setMarkupRate(1.5);
        productDetail.setSize(45);
        productDetail.setInventory(200);
        productDetail.setStatus("inactive");
        productDetail.setDiamondDetails(Collections.emptyList());

        assertEquals(product, productDetail.getProduct());
        assertEquals(setting, productDetail.getSetting());
        assertEquals(1000, productDetail.getLaborCost());
        assertEquals(1.5, productDetail.getMarkupRate());
        assertEquals(45, productDetail.getSize());
        assertEquals(200, productDetail.getInventory());
        assertEquals("inactive", productDetail.getStatus());
        assertNotNull(productDetail.getDiamondDetails());
        assertTrue(productDetail.getDiamondDetails().isEmpty());
    }
}
