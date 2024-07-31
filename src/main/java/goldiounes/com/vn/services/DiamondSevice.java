package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Diamond;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondSevice {
    @Autowired
    private DiamondRepo diamondRepo;

    public List<Diamond> findAll() {
        return diamondRepo.findAll();
    }
    public Diamond findById(int id) {
        return diamondRepo.findById(id).get();
    }
    public Diamond save(Diamond diamond) {
        return diamondRepo.save(diamond);
    }
    public void deleteById(int id) {
        diamondRepo.deleteById(id);
    }
}
