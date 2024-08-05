package goldiounes.com.vn.models;


import goldiounes.com.vn.models.entity.Category;
import goldiounes.com.vn.models.entity.Product;
import goldiounes.com.vn.models.entity.Receipt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {
    @Test
    void testGetterAndSetter() {
        Product product = new Product();
        Receipt receipt = new Receipt(product,10);

        assertEquals(10, receipt.getQuantity());
        assertEquals(product, receipt.getProduct());

        Category category = new Category("AAA");
        Product product1 = new Product(category,"BBB","URL",
                1.2,500,24,10);
        receipt.setProduct(product1);
        receipt.setQuantity(100);

        assertEquals(100, receipt.getQuantity());
        assertEquals(product1, receipt.getProduct());
    }

    @Test
    void testDefaultConstructor() {
        Receipt receipt = new Receipt();

        assertNotNull(receipt);
        assertNull(receipt.getProduct());
        assertEquals(0,receipt.getQuantity());
    }

    @Test
    void testConstructor(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL",
                1.2,500,24,10);
        Receipt receipt = new Receipt(product,10);

        assertNotNull(receipt);
        assertNotNull(receipt.getProduct());
        assertEquals(10,receipt.getQuantity());
    }
}
