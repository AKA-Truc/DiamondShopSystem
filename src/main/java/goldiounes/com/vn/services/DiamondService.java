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

    public DiamondDTO updateDiamond(Integer id, DiamondDTO diamondDTO) {
        return diamondRepo.findById(id)
                .map(existingDiamond -> {
                    // Update the existing diamond with new values
                    existingDiamond.setMeasurement(diamondDTO.getMeasurement());
                    existingDiamond.setCarat(diamondDTO.getCarat());
                    existingDiamond.setClarity(diamondDTO.getClarity());
                    existingDiamond.setColor(diamondDTO.getColor());
                    existingDiamond.setCut(diamondDTO.getCut());
                    existingDiamond.setShape(diamondDTO.getShape());

                    // Save the updated diamond
                    Diamond updatedDiamond = diamondRepo.save(existingDiamond);

                    // Map and return the updated diamond as DTO
                    return modelMapper.map(updatedDiamond, DiamondDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Diamond not found with id: " + id));
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
