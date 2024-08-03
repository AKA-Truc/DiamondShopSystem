package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Certificate;
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

    public Certificate createCertificate(Certificate certificate) {
        if (isGIACodeUnique(certificate.getGIACode())) {
            return certificateRepo.save(certificate);
        } else {
            throw new IllegalArgumentException("GIA Code is not unique.");
        }
    }

    public List<Certificate> findAll() {
        return certificateRepo.findAll();
    }

    public Certificate findById(int id) {
        Optional<Certificate> result = certificateRepo.findById(id);
        return result.orElse(null);
    }

    public Certificate save(Certificate certificate) {
        return certificateRepo.save(certificate);
    }

    public void deleteById(int id) {
        certificateRepo.deleteById(id);
    }

    private boolean isGIACodeUnique(String GIACode) {
        return certificateRepo.findByGIACode(GIACode) == null;
    }

    public Certificate createCertificateWithDiamond(int diamondId, String GIACode) {
        Optional<goldiounes.com.vn.models.Diamond> diamond = diamondRepo.findById(diamondId);
        if (diamond.isPresent()) {
            Certificate certificate = new Certificate(diamond.get(), GIACode);
            return certificateRepo.save(certificate);
        } else {
            throw new IllegalArgumentException("Diamond not found with ID: " + diamondId);
        }
    }
}
