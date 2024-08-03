package goldiounes.com.vn.models;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class DiamondTest {

    @Test
    void testDiamondGettersAndSetters() {
        Diamond diamond = new Diamond();

        diamond.setDiamondID(17);
        diamond.setCarat(50);
        diamond.setColor("D");
        diamond.setClariry(10);
        diamond.setCut("Excellent");
        diamond.setOrigin("Africa");

        assertEquals(17, diamond.getDiamondID());
        assertEquals(50, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals(10, diamond.getClariry());
        assertEquals("Excellent", diamond.getCut());
        assertEquals("Africa", diamond.getOrigin());
    }

    @Test
    void testDiamondConstructor() {
        Diamond diamond = new Diamond(50, "D", 10, "Excellent", "Africa");

        assertEquals(50, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals(10, diamond.getClariry());
        assertEquals("Excellent", diamond.getCut());
        assertEquals("Africa", diamond.getOrigin());
    }

    @Test
    void testDiamondRelationships() {
        Diamond diamond = new Diamond();

        DiamondDetail detail1 = new DiamondDetail();
        DiamondDetail detail2 = new DiamondDetail();
        List<DiamondDetail> diamondDetails = Arrays.asList(detail1, detail2);

        diamond.setDiamondDetails(diamondDetails);
        assertEquals(2, diamond.getDiamondDetails().size());

        Certificate certificate = new Certificate();
        diamond.setCertificate(certificate);
        assertEquals(certificate, diamond.getCertificate());
    }


}
