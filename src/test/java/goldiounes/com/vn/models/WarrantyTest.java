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
    void testGetterAndSetter(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL",
                1.2,500,24);
        User u = new User();


        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar = Calendar.getInstance();
        // Cộng thêm thời hạn bảo hành vào ngày bắt đầu
        calendar.add(Calendar.MONTH, (int) product.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate = calendar.getTime();//2026/5/1

        Warranty w = new Warranty(product, u, endDate);

        Date endDateTest = new Date(2026 - 1900, Calendar.MAY, 1);//2026/5/1

        assertEquals(endDateTest,w.getEndDate());
        assertEquals(product,w.getProduct());
        assertEquals(u,w.getUser());


        User u1 = new User();
        Category category1 = new Category("aaa");
        Product product1 = new Product(category,"bbb","url",
                1.4,600,25);

        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar1 = Calendar.getInstance();

        // Cộng thêm thời hạn bảo hành vào ngày bắt đầu
        calendar1.add(Calendar.MONTH, (int) product1.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate1 = calendar.getTime();//2026/6/2

    w.setEndDate(endDate1);
    w.setUser(u1);
    w.setProduct(product1);

    assertEquals(endDate1,w.getEndDate());
    assertEquals(product1,w.getProduct());
    assertEquals(u1,w.getUser());

    }
    @Test
    void testConstructor(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL",
                1.2,500,24);
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
