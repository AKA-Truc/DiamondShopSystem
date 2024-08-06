package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.CertificateDTO;
import goldiounes.com.vn.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate-management")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/certificates/{diamondId}")
    public CertificateDTO getCertificateByDiamondId(@PathVariable int diamondId) {
        return certificateService.getCertificateByDiamondId(diamondId);
    }

    @PostMapping("/certificates")
    public CertificateDTO createCertificate(@RequestBody CertificateDTO certificateDTO) {
        return certificateService.createCertificate(certificateDTO);
    }

    @DeleteMapping("/certificates/{id}")
    public void deleteCertificate(@PathVariable int id) {
        certificateService.deleteCertificate(id);
    }

    @PutMapping("/certificates/{id}")
    public CertificateDTO updateCertificate(@PathVariable int id, @RequestBody CertificateDTO certificateDTO) {
        return certificateService.updateCertificate(id, certificateDTO);
    }

    @GetMapping("/certificates")
    public List<CertificateDTO> getAllCertificates() {
        return certificateService.getAllCertificates();
    }
}
