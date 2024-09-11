package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.DiamondDTO;
import goldiounes.com.vn.models.entities.Diamond;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiamondService {

    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private ModelMapper modelMapper;


    public List<DiamondDTO> findAll() {
        List<Diamond> diamonds = diamondRepo.findAll();
        if (diamonds.isEmpty()) {
            throw new RuntimeException("No diamonds found");
        }
        List<Diamond> activeDiamonds = new ArrayList<>();
        for (Diamond diamond : diamonds) {
            if(diamond.getStatus().equals("active")){
                activeDiamonds.add(diamond);
            }
        }
        return modelMapper.map(activeDiamonds, new TypeToken<List<DiamondDTO>>() {}.getType());
    }

    public DiamondDTO findById(int id) {
        Diamond diamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        if(!diamond.getStatus().equals("active")){
            throw new RuntimeException("Diamond not found");
        }
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public DiamondDTO createDiamond(DiamondDTO diamondDTO) {
        Diamond diamond = modelMapper.map(diamondDTO, Diamond.class);
        Diamond existingDiamond = diamondRepo.findDiamond(diamondDTO.getCarat(),diamondDTO.getClarity(),diamondDTO.getColor()
                                                 ,diamondDTO.getCut(),diamondDTO.getShape(),diamondDTO.getMeasurement());
        if (existingDiamond != null) {
            throw new RuntimeException("Diamond already exists");
        } else {
            diamond.setStatus("active");
            Diamond newDiamond = diamondRepo.save(diamond);
            return modelMapper.map(newDiamond, DiamondDTO.class);
        }
    }

    public DiamondDTO updateDiamond(int id, DiamondDTO diamondDTO) {
        Diamond diamond = modelMapper.map(diamondDTO, Diamond.class);
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        existingDiamond.setGIACode(diamondDTO.getGIACode());
        existingDiamond.setMeasurement(diamond.getMeasurement());
        existingDiamond.setCarat(diamond.getCarat());
        existingDiamond.setColor(diamond.getColor());
        existingDiamond.setClarity(diamond.getClarity());
        existingDiamond.setCut(diamond.getCut());
        existingDiamond.setShape(diamond.getShape());
        existingDiamond.setPrice(diamond.getPrice());
        diamondRepo.save(existingDiamond);
        return modelMapper.map(existingDiamond, DiamondDTO.class);
    }

    public boolean deleteDiamond(int id) {
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        existingDiamond.setStatus("inactive");
        diamondRepo.save(existingDiamond);
        return true;
    }

    public DiamondDTO getDiamondByGIACode(String GIACode) {
        Diamond existingDiamond = diamondRepo.findDiamondByGIACode(GIACode);
        if (existingDiamond != null) {
            return modelMapper.map(existingDiamond, DiamondDTO.class);
        } else {
            throw new RuntimeException("Diamond not found with GIACode: " + GIACode);
        }
    }
}
