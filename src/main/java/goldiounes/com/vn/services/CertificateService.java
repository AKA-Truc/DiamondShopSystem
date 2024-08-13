package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CertificateDTO;
import goldiounes.com.vn.models.dtos.DiamondDTO;
import goldiounes.com.vn.models.entities.Certificate;
import goldiounes.com.vn.models.entities.Diamond;
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
        Diamond existingDiamond = diamondRepo.findById(diamondId)
                .orElseThrow(()->new RuntimeException("Diamond not found"));
        Certificate existingCertificate = certificateRepo.findByDiamondId(existingDiamond.getDiamondID())
                .orElseThrow(()->new RuntimeException("Certificate not found"));
        return modelMapper.map(existingCertificate, CertificateDTO.class);
    }

    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = modelMapper.map(certificateDTO, Certificate.class);
        Certificate existingCertificate = certificateRepo.findByGIACode(certificate.getGIACode());
        if (existingCertificate != null) {
            throw new RuntimeException("Certificate already exists");
        }
        else {
            Diamond existingDiamond = diamondRepo.findById(certificate.getDiamond().getDiamondID())
                    .orElseThrow(() -> new RuntimeException("Diamond not found"));

            certificate.setDiamond(existingDiamond);
            Certificate savedCertificate = certificateRepo.save(certificate);
            return modelMapper.map(savedCertificate, CertificateDTO.class);
        }
    }

    public void deleteCertificate(int id) {
        Certificate existingCertificate = certificateRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Certificate not found"));
        certificateRepo.delete(existingCertificate);
    }

    public CertificateDTO updateCertificate(int id, CertificateDTO certificateDTO) {
        Certificate certificate = modelMapper.map(certificateDTO, Certificate.class);
        Certificate existingCertificate = certificateRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Certificate not found"));

        Diamond existingDiamond = diamondRepo.findById(certificate.getDiamond().getDiamondID())
                .orElseThrow(()->new RuntimeException("Diamond not found"));

        existingCertificate.setDiamond(existingDiamond);
        existingCertificate.setGIACode(certificate.getGIACode());

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
