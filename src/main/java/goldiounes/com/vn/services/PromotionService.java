package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.PromotionDTO;
import goldiounes.com.vn.models.entities.Promotion;
import goldiounes.com.vn.repositories.PromotionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<PromotionDTO> findAll() {
        List<Promotion> promotions = promotionRepo.findAll();
        return promotions.stream()
                .map(promotion -> modelMapper.map(promotion, PromotionDTO.class))
                .collect(Collectors.toList());
    }

    public PromotionDTO findById(int id) {
        Promotion promotion = promotionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        return modelMapper.map(promotion, PromotionDTO.class);
    }

    public PromotionDTO save(Promotion promotion) {
        Promotion savedPromotion = promotionRepo.save(promotion);
        return modelMapper.map(savedPromotion, PromotionDTO.class);
    }

    public void deleteById(int id) {
        if (!promotionRepo.existsById(id)) {
            throw new RuntimeException("Promotion not found");
        }
        promotionRepo.deleteById(id);
    }

    public PromotionDTO update(Promotion promotion) {
        Promotion existingPromotion = promotionRepo.findById(promotion.getPromotionID()).get();
        if (existingPromotion == null) {
            throw new RuntimeException("Promotion not found");
        }
        existingPromotion.setPromotionName(promotion.getPromotionName());
        existingPromotion.setDescription(promotion.getDescription());
        existingPromotion.setStartDate(promotion.getStartDate());
        existingPromotion.setEndDate(promotion.getEndDate());
        existingPromotion.setDiscountPercent(promotion.getDiscountPercent());
        return modelMapper.map(promotionRepo.save(existingPromotion), PromotionDTO.class);
    }
}
