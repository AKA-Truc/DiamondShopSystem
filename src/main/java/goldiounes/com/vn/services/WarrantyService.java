package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Warranty;
import goldiounes.com.vn.repositories.WarrantyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarrantyService {
    @Autowired
    private WarrantyRepo warrantyRepo;

    public List<Warranty> findAll() {
        return warrantyRepo.findAll();
    }
    public Warranty findById(int id) {
        return warrantyRepo.findById(id).get();
    }
    public Warranty save(Warranty warranty) {
        return warrantyRepo.save(warranty);
    }
    public void deleteById(int id) {
        warrantyRepo.deleteById(id);
    }
}
