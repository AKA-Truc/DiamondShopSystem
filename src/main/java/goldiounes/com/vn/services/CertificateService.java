package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Certificate;
import goldiounes.com.vn.models.Diamond;
import goldiounes.com.vn.repositories.CertificateRepo;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {
    @Autowired
    private CertificateRepo certificateRepo;

    @Autowired
    private DiamondRepo diamondRepo;

    public List<Certificate> findAll() {
        return certificateRepo.findAll();
    }

    public Certificate findById(int id) {
        return certificateRepo.findById(id).get();
    }

    public Certificate save(Certificate certificate) {
        return certificateRepo.save(certificate);
    }

    public void deleteById(int id) {
        certificateRepo.deleteById(id);
    }

    public Certificate createCertificate(Certificate certificate) {
        Diamond diamond = diamondRepo.findById(certificate.getDiamond().getDiamondID()).get();
        if (diamond == null) {
            throw new IllegalArgumentException("Diamond not found with ID: " + certificate.getDiamond().getDiamondID());
        }
        certificate.setDiamond(diamond);
        return certificateRepo.save(certificate);
    }

    public Certificate findByGIACode(String giaCode) {
        return certificateRepo.findByGIACode(giaCode);
    }
}
