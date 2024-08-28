package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.CertificateDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate-management")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/certificates/{diamondId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<CertificateDTO>> getCertificateByDiamondId(@PathVariable int diamondId) {
        CertificateDTO certificate = certificateService.getCertificateByDiamondId(diamondId);
        if (certificate != null) {
            ResponseWrapper<CertificateDTO> response = new ResponseWrapper<>("Certificate retrieved successfully", certificate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<CertificateDTO> response = new ResponseWrapper<>("Certificate not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/certificates")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<CertificateDTO>> createCertificate(@RequestBody CertificateDTO certificateDTO) {
        CertificateDTO createdCertificate = certificateService.createCertificate(certificateDTO);
        ResponseWrapper<CertificateDTO> response = new ResponseWrapper<>("Certificate created successfully", createdCertificate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/certificates/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteCertificate(@PathVariable int id) {
        certificateService.deleteCertificate(id);
        ResponseWrapper<Void> response = new ResponseWrapper<>("Certificate deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/certificates/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<CertificateDTO>> updateCertificate(@PathVariable int id, @RequestBody CertificateDTO certificateDTO) {
        CertificateDTO updatedCertificate = certificateService.updateCertificate(id, certificateDTO);
        if (updatedCertificate != null) {
            ResponseWrapper<CertificateDTO> response = new ResponseWrapper<>("Certificate updated successfully", updatedCertificate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<CertificateDTO> response = new ResponseWrapper<>("Certificate not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/certificates")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALE STAFF', 'ROLE_DELIVERY STAFF', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<List<CertificateDTO>>> getAllCertificates() {
        List<CertificateDTO> certificates = certificateService.getAllCertificates();
        ResponseWrapper<List<CertificateDTO>> response = new ResponseWrapper<>("Certificates retrieved successfully", certificates);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
