package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Promotion;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class PromotionTest {

    @Test
    void testGetterAndSetter() {
        Date startDate = new Date(2024 - 1900, Calendar.MAY, 1);//2024/5/1
        Date endDate = new Date(2024 - 1900, 4, 2);

        Promotion promotion = new Promotion("Promotion_1", "5%", startDate, endDate, 5);

        assertEquals("Promotion_1", promotion.getPromotionName());
        assertEquals("5%", promotion.getDescription());
        assertEquals(startDate, promotion.getStartDate());
        assertEquals(endDate, promotion.getEndDate());
        assertEquals(5, promotion.getDiscountPercent());

        promotion.setPromotionName("Promotion_2");
        promotion.setDescription("10%");
        Date newStartDate = new Date(2024 - 1900, 5, 1); // Tháng 6
        Date newEndDate = new Date(2024 - 1900, 5, 2); // Tháng 6
        promotion.setStartDate(newStartDate);
        promotion.setEndDate(newEndDate);
        promotion.setDiscountPercent(10);

        assertEquals("Promotion_2", promotion.getPromotionName());
        assertEquals("10%", promotion.getDescription());
        assertEquals(newStartDate, promotion.getStartDate());
        assertEquals(newEndDate, promotion.getEndDate());
        assertEquals(10, promotion.getDiscountPercent());
    }

    @Test
    void testConstructor(){
        Date startDate = new Date(2024 - 1900, 4, 1);
        Date endDate = new Date(2024 - 1900, 4, 2);

        Promotion promotion = new Promotion("Promotion_1", "5%", startDate, endDate, 5);
        assertNotNull(promotion);
    }

    @Test
    void testDefaultConstructor(){
        Date startDate = new Date(2024 - 1900, 4, 1);
        Date endDate = new Date(2024 - 1900, 4, 2);
        Promotion promotion = new Promotion();

        assertNotNull(promotion);
        assertNull(promotion.getPromotionName());
        assertNull(promotion.getDescription());
        assertNull(promotion.getStartDate());
        assertNull(promotion.getEndDate());
        assertEquals(0, promotion.getDiscountPercent());
    }
}
