package goldiounes.com.vn.models;

import goldiounes.com.vn.models.entities.Certificate;
import goldiounes.com.vn.models.entities.Diamond;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateTest {

    @Test
    void testGetterAndSetters() {
        Diamond diamond = new Diamond();
        int certificateID = 1;
        String giacode = "GIA123456";

        Certificate certificate = new Certificate();
        certificate.setCertificateID(certificateID);
        certificate.setDiamond(diamond);
        certificate.setGIACode(giacode);

        assertEquals(certificateID, certificate.getCertificateID());
        assertEquals(diamond, certificate.getDiamond());
        assertEquals(giacode, certificate.getGIACode());
    }

    @Test
    void testConstructor() {
        Diamond diamond = new Diamond();
        String giacode = "GIA654321";

        Certificate certificate = new Certificate(diamond, giacode);

        assertNotNull(certificate);
        assertEquals(diamond, certificate.getDiamond());
        assertEquals(giacode, certificate.getGIACode());
    }

    @Test
    void testDefaultConstructor() {
        Certificate certificate = new Certificate();
        assertNotNull(certificate);

        assertEquals(0, certificate.getCertificateID());
        assertNull(certificate.getDiamond());
        assertNull(certificate.getGIACode());
    }
}
