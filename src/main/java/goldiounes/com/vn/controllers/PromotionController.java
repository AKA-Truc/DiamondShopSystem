package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Promotion;
import goldiounes.com.vn.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion-management")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/promotions")
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return promotionService.save(promotion);
    }

    @GetMapping("/promotions")
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = promotionService.findAll();
        if (promotions.isEmpty()) {
            throw new RuntimeException("No promotions found");
        }
        return promotions;
    }

    @GetMapping("/promotions/{id}")
    public Promotion getPromotion(@PathVariable int id) {
        Promotion existingPromotion = promotionService.findById(id);
        if (existingPromotion == null) {
            throw new RuntimeException("Promotion not found");
        }
        return existingPromotion;
    }

    @DeleteMapping("/promotions/{id}")
    public void deletePromotion(@PathVariable int id) {
        Promotion existingPromotion = promotionService.findById(id);
        if (existingPromotion == null) {
            throw new RuntimeException("Promotion not found");
        }
        promotionService.delete(existingPromotion);
    }

    @PutMapping("/promotions/{id}")
    public Promotion updatePromotion(@PathVariable int id, @RequestBody Promotion promotion) {
        Promotion existingPromotion = promotionService.findById(id);
        if (existingPromotion == null) {
            throw new RuntimeException("Promotion not found");
        }
        existingPromotion.setPromotionName(promotion.getPromotionName());
        existingPromotion.setDescription(promotion.getDescription());
        existingPromotion.setStartDate(promotion.getStartDate());
        existingPromotion.setEndDate(promotion.getEndDate());
        existingPromotion.setDiscountPercent(promotion.getDiscountPercent());
        return promotionService.save(existingPromotion);
    }
}
