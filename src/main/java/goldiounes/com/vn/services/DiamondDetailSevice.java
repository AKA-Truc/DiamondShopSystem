package goldiounes.com.vn.services;

import goldiounes.com.vn.models.DiamondDetail;
import goldiounes.com.vn.repositories.DiamondDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondDetailSevice {
    @Autowired
    private DiamondDetailRepo diamondDetailRepo;

    public List<DiamondDetail> findAll() {
        return diamondDetailRepo.findAll();
    }
    public DiamondDetail findById(int id) {
        return diamondDetailRepo.findById(id).get();
    }
    public DiamondDetail save(DiamondDetail diamondDetail) {
        return diamondDetailRepo.save(diamondDetail);
    }
    public void deleteById(int id) {
        diamondDetailRepo.deleteById(id);
    }

    public void delete(DiamondDetail existingDiamondDetail) {

    }

    public List<DiamondDetail> findByDiamondId(int diamondId) {
        return null;
    }
}
