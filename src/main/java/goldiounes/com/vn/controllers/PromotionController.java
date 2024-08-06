package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.PromotionDTO;
import goldiounes.com.vn.models.entity.Promotion;
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
    public PromotionDTO createPromotion(@RequestBody Promotion promotion) {
        return promotionService.save(promotion);
    }

    @GetMapping("/promotions")
    public List<PromotionDTO> getAllPromotions() {
        return promotionService.findAll();
    }

    @GetMapping("/promotions/{id}")
    public PromotionDTO getPromotion(@PathVariable int id) {
        return promotionService.findById(id);
    }

    @DeleteMapping("/promotions/{id}")
    public void deletePromotion(@PathVariable int id) {
        promotionService.deleteById(id);
    }

    @PutMapping("/promotions/{id}")
    public PromotionDTO updatePromotion(@PathVariable int id, @RequestBody Promotion promotion) {
        return promotionService.save(promotion);
    }
}
