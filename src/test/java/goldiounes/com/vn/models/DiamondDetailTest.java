package goldiounes.com.vn.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiamondDetailTest {

    @Test
    void testGetterAndSetters() {
        Diamond diamond = new Diamond();
        ProductDetail productDetail = new ProductDetail();
        int diamondDetailID = 1;
        int quantity = 10;

        DiamondDetail diamondDetail = new DiamondDetail();
        diamondDetail.setDiamondDetailID(diamondDetailID);
        diamondDetail.setDiamond(diamond);
        diamondDetail.setProductDetail(productDetail);
        diamondDetail.setQuantity(quantity);

        assertEquals(diamondDetailID, diamondDetail.getDiamondDetailID());
        assertEquals(diamond, diamondDetail.getDiamond());
        assertEquals(quantity, diamondDetail.getQuantity());
        assertEquals(productDetail, diamondDetail.getProductDetail());
    }

    @Test
    void testConstructor() {
        Diamond diamond = new Diamond();
        int quantity = 5;

        DiamondDetail diamondDetail = new DiamondDetail(diamond, quantity);

        assertNotNull(diamondDetail);
        assertEquals(diamond, diamondDetail.getDiamond());
        assertEquals(quantity, diamondDetail.getQuantity());
    }

    @Test
    void testDefaultConstructor() {
        DiamondDetail diamondDetail = new DiamondDetail();
        assertNotNull(diamondDetail);

        assertEquals(0, diamondDetail.getDiamondDetailID());
        assertNull(diamondDetail.getDiamond());
        assertEquals(0, diamondDetail.getQuantity());
        assertNull(diamondDetail.getProductDetail());
    }
}
