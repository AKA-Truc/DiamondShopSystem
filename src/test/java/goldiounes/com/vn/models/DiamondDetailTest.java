package goldiounes.com.vn.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class DiamondDetailTest {

    @Test
    void testDiamondDetailGettersAndSetters() {
        DiamondDetail diamondDetail = new DiamondDetail();

        Diamond mockDiamond = Mockito.mock(Diamond.class);
        ProductDetail mockProductDetail = Mockito.mock(ProductDetail.class);

        diamondDetail.setDiamondDetailID(1);
        diamondDetail.setDiamond(mockDiamond);
        diamondDetail.setProductDetail(mockProductDetail);
        diamondDetail.setQuantity(5);

        assertEquals(1, diamondDetail.getDiamondDetailID());
        assertEquals(mockDiamond, diamondDetail.getDiamond());
        assertEquals(mockProductDetail, diamondDetail.getProductDetail());
        assertEquals(5, diamondDetail.getQuantity());
    }

    @Test
    void testDiamondDetailConstructor() {
        Diamond mockDiamond = Mockito.mock(Diamond.class);

        DiamondDetail diamondDetail = new DiamondDetail(mockDiamond, 5);

        assertEquals(mockDiamond, diamondDetail.getDiamond());
        assertEquals(5, diamondDetail.getQuantity());
    }

    @Test
    void testDiamondDetailProductDetailRelationship() {
        DiamondDetail diamondDetail = new DiamondDetail();

        ProductDetail mockProductDetail = Mockito.mock(ProductDetail.class);
        diamondDetail.setProductDetail(mockProductDetail);

        assertEquals(mockProductDetail, diamondDetail.getProductDetail());
    }
}
