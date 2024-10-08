package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.DiamondDetailDTO;
import goldiounes.com.vn.models.entities.Diamond;
import goldiounes.com.vn.models.entities.DiamondDetail;
import goldiounes.com.vn.models.entities.ProductDetail;
import goldiounes.com.vn.repositories.DiamondDetailRepo;
import goldiounes.com.vn.repositories.DiamondRepo;
import goldiounes.com.vn.repositories.ProductDetailRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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


    public List<DiamondDetailDTO> findAll() {
        List<DiamondDetail> diamondDetails = diamondDetailRepo.findAll();
        if (diamondDetails.isEmpty()) {
            throw new RuntimeException("Diamond Details List is empty");
        }
        return modelMapper.map(diamondDetails,new TypeToken<List<DiamondDetailDTO>>(){}.getType());
    }

    public DiamondDetailDTO findById(int id) {
        DiamondDetail diamondDetail = diamondDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));
        return modelMapper.map(diamondDetail, DiamondDetailDTO.class);
    }

    public DiamondDetailDTO createDiamondDetail(DiamondDetailDTO diamondDetailDTO) {
        DiamondDetail diamondDetail = modelMapper.map(diamondDetailDTO, DiamondDetail.class);
        Diamond existingDiamond = diamondRepo.findById(diamondDetail.getDiamond().getDiamondID())
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        diamondDetail.setDiamond(existingDiamond);
        ProductDetail existingProductDetail = productDetailRepo.findById(diamondDetail.getProductDetail().getProductDetailID())
                .orElseThrow(() -> new RuntimeException("ProductDetail not found"));
        diamondDetail.setProductDetail(existingProductDetail);
        if (Objects.equals(diamondDetail.getTypeDiamond(), "Kim cương chính")) {
            diamondDetail.setQuantity(1);
        }
        diamondDetailRepo.save(diamondDetail);
        return modelMapper.map(diamondDetail, DiamondDetailDTO.class);
    }

    public boolean deleteById(int id) {
        DiamondDetail existingDiamondDetail = diamondDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));
        diamondDetailRepo.deleteById(existingDiamondDetail.getDiamondDetailID());
        return true;
    }

    public DiamondDetailDTO update(int id, DiamondDetailDTO diamondDetailDTO) {
        DiamondDetail diamondDetail = modelMapper.map(diamondDetailDTO, DiamondDetail.class);
        DiamondDetail existingDiamondDetail = diamondDetailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DiamondDetail not found"));
        Diamond existingDiamond = diamondRepo.findById(diamondDetail.getDiamond().getDiamondID())
                .orElseThrow(() -> new RuntimeException("Diamond not found"));
        ProductDetail existingProductDetail = productDetailRepo.findById(diamondDetail.getProductDetail().getProductDetailID())
                .orElseThrow(() -> new RuntimeException("ProductDetail not found"));
        existingDiamondDetail.setProductDetail(existingProductDetail);
        existingDiamondDetail.setDiamond(existingDiamond);
        existingDiamondDetail.setQuantity(diamondDetail.getQuantity());
        diamondDetailRepo.save(existingDiamondDetail);
        return modelMapper.map(existingDiamondDetail, DiamondDetailDTO.class);
    }
}
