package goldiounes.com.vn.models;
import goldiounes.com.vn.models.entity.Category;
import goldiounes.com.vn.models.entity.Product;
import goldiounes.com.vn.models.entity.User;
import goldiounes.com.vn.models.entity.Warranty;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class WarrantyTest {
    @Test
    void testGetterAndSetter(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL",
                1.2,500,24,10);
        User u = new User();

        Date startDate = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1

        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Cộng thêm thời hạn bảo hành vào ngày bắt đầu
        calendar.add(Calendar.MONTH, (int) product.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate = calendar.getTime();//2026/5/1

        Warranty w = new Warranty(product, u,"AAA",startDate,endDate);

        Date startDateTest = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1
        Date endDateTest = new Date(2026 - 1900, Calendar.MAY, 1);//2026/5/1

        assertEquals(startDateTest,w.getStartDate());
        assertEquals(endDateTest,w.getEndDate());
        assertEquals(product,w.getProduct());
        assertEquals(u,w.getUser());
        assertEquals("AAA",w.getWarrantyDetails());


        User u1=new User();
        Category category1 = new Category("aaa");
        Product product1 = new Product(category,"bbb","url",
                1.4,600,25,11);
        Date startDate1 = new Date(2024 - 1900, Calendar.MAY, 2);//2024/5/2

        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);

        // Cộng thêm thời hạn bảo hành vào ngày bắt đầu
        calendar1.add(Calendar.MONTH, (int) product1.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate1 = calendar.getTime();//2026/6/2

    w.setStartDate(startDate1);
    w.setEndDate(endDate1);
    w.setUser(u1);
    w.setProduct(product1);
    w.setWarrantyDetails("aaaaa");

    assertEquals(startDate1,w.getStartDate());
    assertEquals(endDate1,w.getEndDate());
    assertEquals(product1,w.getProduct());
    assertEquals(u1,w.getUser());
    assertEquals("aaaaa",w.getWarrantyDetails());



    }
    @Test
    void testConstructor(){
        Category category = new Category("AAA");
        Product product = new Product(category,"BBB","URL",
                1.2,500,24,10);
        User u = new User();

        Date startDate = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1

        // Tạo Calendar instance và set thời gian bảo hành vào
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Cộng thêm thời hạn bảo hành vào ngày bắt đầu
        calendar.add(Calendar.MONTH, (int) product.getWarrantyPeriod());

        // Lấy ngày kết thúc bảo hành từ Calendar
        Date endDate = calendar.getTime();//2026/5/1

        Warranty w = new Warranty(product, u,"AAA",startDate,endDate);

        assertNotNull(w.getStartDate());
        assertNotNull(w.getEndDate());
        assertNotNull(w.getProduct());
        assertNotNull(w.getUser());
        assertNotNull(w.getWarrantyDetails());
        assertNotNull(w);

    }
    @Test
    void testDefaultConstructor(){
        Warranty w = new Warranty();
        assertNull(w.getStartDate());
        assertNull(w.getEndDate());
        assertNull(w.getProduct());
        assertNull(w.getUser());
        assertNull(w.getWarrantyDetails());
        assertNotNull(w);
    }
}
