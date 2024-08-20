package goldiounes.com.vn.models;
import goldiounes.com.vn.models.entities.Diamond;
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
        diamond.setPrice(1000000);

        assertEquals(17, diamond.getDiamondID());
        assertEquals(0.5, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals("IF", diamond.getClarity());
        assertEquals("Excellent", diamond.getCut());
        assertEquals("Africa", diamond.getOrigin());
        assertEquals(1000000, diamond.getPrice());
    }

    @Test
    void testDiamondConstructor() {
        Diamond diamond = new Diamond( 0.5, "D", "IF", "Excellent", "Africa",10000000);

        assertEquals(0.5, diamond.getCarat());
        assertEquals("D", diamond.getColor());
        assertEquals("IF", diamond.getClarity());
        assertEquals("Excellent", diamond.getCut());
        assertEquals("Africa", diamond.getOrigin());
        assertEquals(10000000, diamond.getDiamondID());
    }

}
