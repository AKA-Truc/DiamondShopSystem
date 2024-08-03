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
        diamond.setCarat(0.5);
        diamond.setColor("D");
        diamond.setClarity("IF");
        diamond.setCut("Excellent");
        diamond.setOrigin("Africa");

        assertEquals(17, diamond.getDiamondID());
        assertEquals(0.5, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals("IF", diamond.getClarity());
        assertEquals("Excellent", diamond.getCut());
        assertEquals("Africa", diamond.getOrigin());
    }

    @Test
    void testDiamondConstructor() {
        Diamond diamond = new Diamond( 0.5, "D", "IF", "Excellent", "Africa");

        assertEquals(0.5F, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals("IF", diamond.getClarity());
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
