package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.DiamondDTO;
import goldiounes.com.vn.models.entities.Certificate;
import goldiounes.com.vn.models.entities.Diamond;
import goldiounes.com.vn.repositories.CertificateRepo;
import goldiounes.com.vn.repositories.DiamondDetailRepo;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondService {

    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private CertificateRepo certificateRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    public List<DiamondDTO> findAll() {
        List<Diamond> diamonds = diamondRepo.findAll();
        if (diamonds.isEmpty()) {
            throw new RuntimeException("No diamonds found");
        }
        return modelMapper.map(diamonds, new TypeToken<List<DiamondDTO>>() {}.getType());
    }

    public DiamondDTO findById(int id) {
        Diamond diamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public DiamondDTO createDiamond(DiamondDTO diamondDTO) {
        Diamond diamond = modelMapper.map(diamondDTO, Diamond.class);
        Diamond existingDiamond = diamondRepo.findDiamond(diamondDTO.getCarat(),diamondDTO.getClarity(),diamondDTO.getColor()
                                                 ,diamondDTO.getCut(),diamondDTO.getOrigin());
        if (existingDiamond != null) {
            throw new RuntimeException("Diamond already exists");
        } else {
            Diamond newDiamond = diamondRepo.save(diamond);
            return modelMapper.map(newDiamond, DiamondDTO.class);
        }
    }

    public DiamondDTO updateDiamond(int id, DiamondDTO diamondDTO) {
        Diamond diamond = modelMapper.map(diamondDTO, Diamond.class);
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        existingDiamond.setCarat(diamond.getCarat());
        existingDiamond.setColor(diamond.getColor());
        existingDiamond.setClarity(diamond.getClarity());
        existingDiamond.setCut(diamond.getCut());
        existingDiamond.setOrigin(diamond.getOrigin());
        existingDiamond.setPrice(diamond.getPrice());
        diamondRepo.save(existingDiamond);
        return modelMapper.map(existingDiamond, DiamondDTO.class);
    }

    public boolean deleteDiamond(int id) {
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        Certificate certificate = certificateRepo.findByDiamondId(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        certificateRepo.delete(certificate);
        diamondRepo.deleteById(existingDiamond.getDiamondID());
        return true;
    }
}
