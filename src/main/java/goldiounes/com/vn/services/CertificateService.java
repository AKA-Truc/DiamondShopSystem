package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Certificate;
import goldiounes.com.vn.repositories.CertificateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateService {
    @Autowired
    private CertificateRepo certificateRepo;

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
}
