package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Promotion;
import goldiounes.com.vn.repositories.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepo promotionRepo;

    public List<Promotion> findAll() {
        return promotionRepo.findAll();
    }
    public Promotion findById(int id) {
        return promotionRepo.findById(id).get();
    }
    public Promotion save(Promotion promotion) {
        return promotionRepo.save(promotion);
    }
    public void deleteById(int id) {
        promotionRepo.deleteById(id);
    }
}
