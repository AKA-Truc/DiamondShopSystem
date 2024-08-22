package goldiounes.com.vn.models;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.models.entities.Warranty;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class WarrantyTest {
    @Test
    void testGetterAndSetter() {
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL","SubURL",24,"active");
        User u = new User();

        // Set the start date for the warranty period (2024/5/1)
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MAY, 1);
        // Add the warranty period in months to the start date
        calendar.add(Calendar.MONTH, (int) product.getWarrantyPeriod());

        // Get the warranty end date (2026/5/1)
        Date endDate = calendar.getTime();

        Warranty w = new Warranty(product, u, endDate);

        // Compare using timestamps
        assertEquals(endDate.getTime(), w.getEndDate().getTime());
        assertEquals(product, w.getProduct());
        assertEquals(u, w.getUser());

        // Testing setters
        User u1 = new User();
        Category category1 = new Category("aaa");
        Product product1 = new Product(category1,"BBB","URL","SubURL",24,"active");

        // Reset calendar and set a different warranty period
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.MAY, 1);
        calendar1.add(Calendar.MONTH, (int) product1.getWarrantyPeriod());

        // Get the new warranty end date
        Date endDate1 = calendar1.getTime();

        // Set new values
        w.setEndDate(endDate1);
        w.setUser(u1);
        w.setProduct(product1);

        // Assertions using time in milliseconds for comparison
        assertEquals(endDate1.getTime(), w.getEndDate().getTime());
        assertEquals(product1, w.getProduct());
        assertEquals(u1, w.getUser());
    }

    @Test
    void testConstructor(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL","SubURL",24,"active");
        User u = new User();

        Date startDate = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1

        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Cộng thêm thời hạn bảo hành vào ngày bắt đầu
        calendar.add(Calendar.MONTH, (int) product.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate = calendar.getTime();//2026/5/1

        Warranty w = new Warranty(product, u, endDate);

        assertNotNull(w.getEndDate());
        assertNotNull(w.getProduct());
        assertNotNull(w.getUser());
        assertNotNull(w);

    }
    @Test
    void testDefaultConstructor(){
        Warranty w = new Warranty();
        assertNull(w.getEndDate());
        assertNull(w.getProduct());
        assertNull(w.getUser());
        assertNotNull(w);
    }
}
