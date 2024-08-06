package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.DiamondDTO;
import goldiounes.com.vn.models.dto.DiamondDetailDTO;
import goldiounes.com.vn.models.entity.Diamond;
import goldiounes.com.vn.models.entity.DiamondDetail;
import goldiounes.com.vn.repositories.DiamondRepo;
import goldiounes.com.vn.repositories.DiamondDetailRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiamondService {

    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    public DiamondDTO findDiamond(Double carat, String clarity, String color, String cut, String origin) {
        Diamond diamond = diamondRepo.findDiamond(carat, clarity, color, cut, origin);
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public DiamondDTO createDiamond(DiamondDTO diamondDTO) {
        Diamond diamond = modelMapper.map(diamondDTO, Diamond.class);
        Optional<Diamond> existingDiamond = diamondRepo.findById(diamond.getDiamondID());
        if (existingDiamond.isPresent()) {
            throw new RuntimeException("Diamond already exists");
        }
        Diamond savedDiamond = diamondRepo.save(diamond);
        return modelMapper.map(savedDiamond, DiamondDTO.class);
    }

    public DiamondDTO findById(int id) {
        Diamond diamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        return modelMapper.map(diamond, DiamondDTO.class);
    }

    public List<DiamondDTO> findAll() {
        List<Diamond> diamonds = diamondRepo.findAll();
        if (diamonds.isEmpty()) {
            throw new RuntimeException("No diamonds found");
        }
        return diamonds.stream()
                .map(diamond -> modelMapper.map(diamond, DiamondDTO.class))
                .collect(Collectors.toList());
    }

    public DiamondDTO updateDiamond(int id, DiamondDTO diamondDTO) {
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));

        existingDiamond.setCarat(diamondDTO.getCarat());
        existingDiamond.setColor(diamondDTO.getColor());
        existingDiamond.setClarity(diamondDTO.getClarity());
        existingDiamond.setCut(diamondDTO.getCut());
        existingDiamond.setOrigin(diamondDTO.getOrigin());

        Diamond updatedDiamond = diamondRepo.save(existingDiamond);
        return modelMapper.map(updatedDiamond, DiamondDTO.class);
    }

    public void deleteDiamond(int id) {
        Diamond existingDiamond = diamondRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        diamondRepo.delete(existingDiamond);
    }

    public DiamondDetailDTO addDiamondDetail(int diamondId, DiamondDetailDTO diamondDetailDTO) {
        Diamond diamond = diamondRepo.findById(diamondId)
                .orElseThrow(() -> new RuntimeException("Diamond not found"));

        DiamondDetail diamondDetail = modelMapper.map(diamondDetailDTO, DiamondDetail.class);
        diamondDetail.setDiamond(diamond);

        DiamondDetail savedDiamondDetail = diamondDetailRepo.save(diamondDetail);
        return modelMapper.map(savedDiamondDetail, DiamondDetailDTO.class);
    }

    public List<DiamondDetailDTO> findDiamondDetailsByDiamondId(int diamondId) {
        List<DiamondDetail> diamondDetails = diamondDetailRepo.findByDiamondId(diamondId);
        return diamondDetails.stream()
                .map(detail -> modelMapper.map(detail, DiamondDetailDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteDiamondDetail(int detailId) {
        DiamondDetail existingDetail = diamondDetailRepo.findById(detailId)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));
        diamondDetailRepo.delete(existingDetail);
    }

    public DiamondDetailDTO updateDiamondDetail(int detailId, DiamondDetailDTO diamondDetailDTO) {
        DiamondDetail existingDetail = diamondDetailRepo.findById(detailId)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));

        existingDetail.setQuantity(diamondDetailDTO.getQuantity());

        DiamondDetail updatedDetail = diamondDetailRepo.save(existingDetail);
        return modelMapper.map(updatedDetail, DiamondDetailDTO.class);
    }
}
