package goldiounes.com.vn.models;
import goldiounes.com.vn.models.entity.Diamond;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(0.5, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals("IF", diamond.getClarity());
        assertEquals("Excellent", diamond.getCut());
        assertEquals("Africa", diamond.getOrigin());
    }

}
