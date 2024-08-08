package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.DiamondDetailDTO;
import goldiounes.com.vn.models.entity.Diamond;
import goldiounes.com.vn.models.entity.DiamondDetail;
import goldiounes.com.vn.models.entity.ProductDetail;
import goldiounes.com.vn.repositories.DiamondDetailRepo;
import goldiounes.com.vn.repositories.DiamondRepo;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiamondDetailService {

    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private ProductDetailRepo productDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<DiamondDetailDTO> findByDiamondId(int diamondId) {
        List<DiamondDetail> diamondDetails = diamondDetailRepo.findByDiamondId(diamondId);
        return diamondDetails.stream()
                .map(diamondDetail -> modelMapper.map(diamondDetail, DiamondDetailDTO.class))
                .collect(Collectors.toList());
    }

    public List<DiamondDetailDTO> findAll() {
        List<DiamondDetail> diamondDetails = diamondDetailRepo.findAll();
        return diamondDetails.stream()
                .map(diamondDetail -> modelMapper.map(diamondDetail, DiamondDetailDTO.class))
                .collect(Collectors.toList());
    }

    public DiamondDetailDTO findById(int id) {
        DiamondDetail diamondDetail = diamondDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));
        return modelMapper.map(diamondDetail, DiamondDetailDTO.class);
    }

    public DiamondDetailDTO save(DiamondDetailDTO diamondDetailDTO) {
        Diamond diamond = diamondRepo.findById(diamondDetailDTO.getDiamond().getDiamondID())
                .orElseThrow(() -> new RuntimeException("Diamond not found"));

        DiamondDetail diamondDetail = modelMapper.map(diamondDetailDTO, DiamondDetail.class);
        diamondDetail.setDiamond(diamond);

        DiamondDetail savedDiamondDetail = diamondDetailRepo.save(diamondDetail);
        return modelMapper.map(savedDiamondDetail, DiamondDetailDTO.class);
    }

    public void deleteById(int id) {
        if (!diamondDetailRepo.existsById(id)) {
            throw new RuntimeException("DiamondDetail not found");
        }
        diamondDetailRepo.deleteById(id);
    }

    public DiamondDetailDTO update(int id, DiamondDetailDTO diamondDetailDTO) {
        DiamondDetail existingDiamondDetail = diamondDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));

        Diamond diamond = diamondRepo.findById(diamondDetailDTO.getDiamond().getDiamondID())
                .orElseThrow(() -> new RuntimeException("Diamond not found"));

        existingDiamondDetail.setDiamond(diamond);
        existingDiamondDetail.setQuantity(diamondDetailDTO.getQuantity());
//        existingDiamondDetail.setProductDetail(diamondDetailDTO.getProductDetail());

        DiamondDetail savedDiamondDetail = diamondDetailRepo.save(existingDiamondDetail);
        return modelMapper.map(savedDiamondDetail, DiamondDetailDTO.class);
    }
}
