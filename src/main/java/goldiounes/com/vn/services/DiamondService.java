package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.DiamondDTO;
import goldiounes.com.vn.models.entity.Diamond;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiamondService {

    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<DiamondDTO> findAll() {
        List<Diamond> diamonds = diamondRepo.findAll();
        return diamonds.stream()
                .map(diamond -> modelMapper.map(diamond, DiamondDTO.class))
                .collect(Collectors.toList());
    }

    public DiamondDTO findById(int id) {
        Diamond diamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public DiamondDTO save(DiamondDTO diamondDTO) {
        Diamond diamond = modelMapper.map(diamondDTO, Diamond.class);
        Diamond savedDiamond = diamondRepo.save(diamond);
        return modelMapper.map(savedDiamond, DiamondDTO.class);
    }

    public void deleteById(int id) {
        if (!diamondRepo.existsById(id)) {
            throw new RuntimeException("Diamond not found");
        }
        diamondRepo.deleteById(id);
    }

//    public DiamondDTO update(int id, DiamondDTO diamondDTO) {
//        Diamond existingDiamond = diamondRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Diamond not found"));
//
//        existingDiamond.setCarat(diamondDTO.getCarat());
//        existingDiamond.setColor(diamondDTO.getColor());
//        existingDiamond.setClarity(diamondDTO.getClarity());
//        existingDiamond.setCut(diamondDTO.getCut());
//        existingDiamond.setOrigin(diamondDTO.getOrigin());
//
//        Diamond updatedDiamond = diamondRepo.save(existingDiamond);
//        return modelMapper.map(updatedDiamond, DiamondDTO.class);
//    }

    public DiamondDTO createDiamond(DiamondDTO diamondDTO) {
        Diamond diamond = new Diamond();
        diamond.setCarat(diamondDTO.getCarat());
        diamond.setColor(diamondDTO.getColor());
        diamond.setClarity(diamondDTO.getClarity());
        diamond.setCut(diamondDTO.getCut());
        diamond.setOrigin(diamondDTO.getOrigin());
        Diamond savedDiamond = diamondRepo.save(diamond);
        return convertToDTO(savedDiamond);
    }

    public void updateDiamond(int id, DiamondDTO diamondDTO) {
        Diamond diamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found with ID: " + id));
        diamond.setCarat(diamondDTO.getCarat());
        diamond.setColor(diamondDTO.getColor());
        diamond.setClarity(diamondDTO.getClarity());
        diamond.setCut(diamondDTO.getCut());
        diamond.setOrigin(diamondDTO.getOrigin());
        diamondRepo.save(diamond);
    }

    public void deleteDiamond(int id) {
        if (!diamondRepo.existsById(id)) {
            throw new RuntimeException("Diamond not found with ID: " + id);
        }
        diamondRepo.deleteById(id);
    }

    private DiamondDTO convertToDTO(Diamond diamond) {
        return new DiamondDTO(diamond.getDiamondID(), diamond.getCarat(), diamond.getColor(),
                diamond.getClarity(), diamond.getCut(), diamond.getOrigin());
    }
}
