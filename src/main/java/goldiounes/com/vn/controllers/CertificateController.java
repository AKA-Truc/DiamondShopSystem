//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.models.entity.Blog;
//import goldiounes.com.vn.models.entity.Certificate;
//import goldiounes.com.vn.services.CertificateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/certificate-management")
//public class CertificateController {
//
//    @Autowired
//    private CertificateService certificateService;
//
//    @PostMapping("/certificates")
//    public Certificate createCertificate(@RequestBody Certificate certificate) {
//        Certificate existingcertificate = certificateService.findByGIACode(certificate.getGIACode());
//        if (existingcertificate != null ) {
//            throw new RuntimeException("Certificate already exists");
//        }
//        return certificateService.createCertificate(certificate);
//    }
//
//
//    @GetMapping("/certificates")
//    public List<Certificate> getAllCertificates() {
//        List<Certificate> certificates = certificateService.findAll();
//        if (certificates.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No certificates found");
//        }
//        return certificates;
//    }
//
//    @GetMapping("/certificates/{id}")
//    public Certificate getCertificateById(@PathVariable int id) {
//        Certificate certificate = certificateService.findById(id);
//        if (certificate != null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found");
//        }
//        return certificate;
//    }
//
//    @PutMapping("/certificates/{id}")
//    public Certificate updateCertificate(@PathVariable int id, @RequestBody Certificate certificate) {
//        Certificate existingCertificate = certificateService.findById(id);
//        if (existingCertificate == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found");
//        }
//        existingCertificate.setGIACode(certificate.getGIACode());
//        return certificateService.save(existingCertificate);
//    }
//
//    @DeleteMapping("/certificates/{id}")
//    public void deleteCertificate(@PathVariable int id) {
//        Certificate existingCertificate = certificateService.findById(id);
//        if (existingCertificate == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found");
//        }
//        certificateService.deleteById(existingCertificate.getCertificateID());
//    }
//}
