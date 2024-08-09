package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.CertificateDTO;
import goldiounes.com.vn.models.entity.Certificate;
import goldiounes.com.vn.models.entity.Diamond;
import goldiounes.com.vn.repositories.CertificateRepo;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    @Autowired
    private ModelMapper modelMapper;

    public CertificateDTO getCertificateByDiamondId(int diamondId) {
        Optional<Diamond> existingDiamond = diamondRepo.findById(diamondId);
        if (!existingDiamond.isPresent()) {
            throw new RuntimeException("Diamond not found");
        }
        Optional<Certificate> existingCertificate = certificateRepo.findByDiamondId(diamondId);
        if (!existingCertificate.isPresent()) {
            throw new RuntimeException("Certificate not found");
        }
        return modelMapper.map(existingCertificate.get(), CertificateDTO.class);
    }

    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {
        Optional<Diamond> existingDiamond = diamondRepo.findById(certificateDTO.getDiamond().getDiamondID());
        if (!existingDiamond.isPresent()) {
            throw new RuntimeException("Diamond not found");
        }
        Certificate certificate = new Certificate();
        certificate.setDiamond(existingDiamond.get());
        certificate.setGIACode(certificateDTO.getGIACode());
        Certificate savedCertificate = certificateRepo.save(certificate);
        return modelMapper.map(savedCertificate, CertificateDTO.class);
    }

    public void deleteCertificate(int id) {
        Optional<Certificate> existingCertificate = certificateRepo.findById(id);
        if (!existingCertificate.isPresent()) {
            throw new RuntimeException("Certificate not found");
        }
        certificateRepo.delete(existingCertificate.get());
    }

    public CertificateDTO updateCertificate(int id, CertificateDTO certificateDTO) {
        Optional<Certificate> existingCertificate = certificateRepo.findById(id);
        if (!existingCertificate.isPresent()) {
            throw new RuntimeException("Certificate not found");
        }

        Optional<Diamond> existingDiamond = diamondRepo.findById(certificateDTO.getDiamond().getDiamondID());
        if (!existingDiamond.isPresent()) {
            throw new RuntimeException("Diamond not found");
        }

        Certificate certificate = existingCertificate.get();
        certificate.setDiamond(existingDiamond.get());
        certificate.setGIACode(certificateDTO.getGIACode());

        Certificate savedCertificate = certificateRepo.save(certificate);
        return modelMapper.map(savedCertificate, CertificateDTO.class);
    }

    public List<CertificateDTO> getAllCertificates() {
        List<Certificate> certificates = certificateRepo.findAll();
        if (certificates.isEmpty()) {
            throw new RuntimeException("No certificates found");
        }
        return modelMapper.map(certificates, new TypeToken<List<CertificateDTO>>() {}.getType());
    }

}
