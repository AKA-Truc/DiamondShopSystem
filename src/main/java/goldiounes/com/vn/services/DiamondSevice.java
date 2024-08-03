package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Diamond;
import goldiounes.com.vn.models.DiamondDetail;
import goldiounes.com.vn.repositories.DiamondRepo;
import goldiounes.com.vn.repositories.DiamondDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiamondSevice {
    @Autowired
    private DiamondRepo diamondRepo;

    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    public Diamond createDiamond(Diamond diamond) {
        Optional<Diamond> existingDiamond = diamondRepo.findById(diamond.getDiamondID());
        if (existingDiamond.isPresent()) {
            throw new RuntimeException("Diamond already exists");
        }
        return diamondRepo.save(diamond);
    }

    public Diamond findById(int id) {
        Optional<Diamond> diamond = diamondRepo.findById(id);
        if (!diamond.isPresent()) {
            throw new RuntimeException("Diamond not found");
        }
        return diamond.get();
    }

    public List<Diamond> findAll() {
        List<Diamond> diamonds = diamondRepo.findAll();
        if (diamonds.isEmpty()) {
            throw new RuntimeException("No diamonds found");
        }
        return diamonds;
    }

    public Diamond updateDiamond(int id, Diamond diamond) {
        Diamond existingDiamond = findById(id);
        existingDiamond.setCarat(diamond.getCarat());
        existingDiamond.setColor(diamond.getColor());
        existingDiamond.setClarity(diamond.getClarity());
        existingDiamond.setCut(diamond.getCut());
        existingDiamond.setOrigin(diamond.getOrigin());

        return diamondRepo.save(existingDiamond);
    }

    public void deleteDiamond(int id) {
        Diamond existingDiamond = findById(id);
        if (existingDiamond == null) {
            throw new RuntimeException("Diamond not found");
        }
        diamondRepo.delete(existingDiamond);
    }

    public DiamondDetail addDiamondDetail(int diamondId, DiamondDetail diamondDetail) {
        Diamond diamond = findById(diamondId);
        if (diamond == null) {
            throw new RuntimeException("Diamond not found");
        }
        diamondDetail.setDiamond(diamond);
        return diamondDetailRepo.save(diamondDetail);
    }

    public List<DiamondDetail> findDiamondDetailsByDiamondId(int diamondId) {
//        return diamondDetailRepo.findByDiamondId(diamondId);
        return null; //
    }

    public void deleteDiamondDetail(int detailId) {
        Optional<DiamondDetail> existingDetail = diamondDetailRepo.findById(detailId);
        if (!existingDetail.isPresent()) {
            throw new RuntimeException("DiamondDetail not found");
        }
        diamondDetailRepo.delete(existingDetail.get());
    }

    public DiamondDetail updateDiamondDetail(int detailId, DiamondDetail diamondDetail) {
        DiamondDetail existingDetail = diamondDetailRepo.findById(detailId).orElseThrow(() -> new RuntimeException("DiamondDetail not found"));

//        existingDetail.setDetail(diamondDetail.getDetail());
        existingDetail.setQuantity(diamondDetail.getQuantity());
//        existingDetail.setPrice(diamondDetail.getPrice());

        return diamondDetailRepo.save(existingDetail);
    }
}
