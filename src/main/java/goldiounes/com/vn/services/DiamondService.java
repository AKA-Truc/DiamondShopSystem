package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.DiamondDTO;
import goldiounes.com.vn.models.entity.Diamond;
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
        Diamond diamond = diamondRepo.findById(id).orElseThrow(() -> new RuntimeException("Diamond not found"));
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public DiamondDTO createDiamond(DiamondDTO diamondDTO) {
        Diamond existingDiamond = diamondRepo.findDiamond(diamondDTO.getCarat(),diamondDTO.getClarity(),diamondDTO.getColor()
                                                 ,diamondDTO.getCut(),diamondDTO.getOrigin());
        if (existingDiamond.getDiamondDetails() == null) {
            throw new RuntimeException("Diamond Details not found");
        }
        Diamond newDiamond = diamondRepo.save(existingDiamond);
        return modelMapper.map(newDiamond, DiamondDTO.class);
    }

    public DiamondDTO updateDiamond(int id, DiamondDTO diamondDTO) {
        Diamond diamond = diamondRepo.findById(id).orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        diamond.setCarat(diamondDTO.getCarat());
        diamond.setColor(diamondDTO.getColor());
        diamond.setClarity(diamondDTO.getClarity());
        diamond.setCut(diamondDTO.getCut());
        diamond.setOrigin(diamondDTO.getOrigin());
        diamondRepo.save(diamond);
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public void deleteDiamond(int id) {
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        diamondRepo.deleteById(id);
    }
}
