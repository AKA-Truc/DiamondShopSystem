package goldiounes.com.vn.models;

import goldiounes.com.vn.controllers.CertificateController;
import goldiounes.com.vn.models.Certificate;
import goldiounes.com.vn.services.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class CertificateTest {

    @Mock
    private CertificateService certificateService;

    @InjectMocks
    private CertificateController certificateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCertificates() {
        Certificate certificate1 = new Certificate();
        Certificate certificate2 = new Certificate();
        List<Certificate> certificates = Arrays.asList(certificate1, certificate2);

        when(certificateService.findAll()).thenReturn(certificates);

        ResponseEntity<List<Certificate>> response = certificateController.getAllCertificates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(certificateService, times(1)).findAll();
    }

    @Test
    void testCreateCertificate() {
        Certificate certificate = new Certificate();
        when(certificateService.save(any(Certificate.class))).thenReturn(certificate);

        ResponseEntity<Certificate> response = certificateController.createCertificate(certificate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(certificate, response.getBody());
        verify(certificateService, times(1)).save(certificate);
    }

}
