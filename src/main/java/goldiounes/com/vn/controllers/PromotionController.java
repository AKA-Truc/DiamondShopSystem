package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.PromotionDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion-management")
@RequiredArgsConstructor
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/promotions")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<PromotionDTO>> createPromotion(@RequestBody PromotionDTO promotionDTO) {
        PromotionDTO createdPromotion = promotionService.save(promotionDTO);
        ResponseWrapper<PromotionDTO> response = new ResponseWrapper<>("Promotion created successfully", createdPromotion);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/promotions")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<List<PromotionDTO>>> getAllPromotions() {
        List<PromotionDTO> promotions = promotionService.findAll();
        ResponseWrapper<List<PromotionDTO>> response = new ResponseWrapper<>("Promotions retrieved successfully", promotions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/promotions/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<PromotionDTO>> getPromotion(@PathVariable int id) {
        PromotionDTO promotion = promotionService.findById(id);
        if (promotion != null) {
            ResponseWrapper<PromotionDTO> response = new ResponseWrapper<>("Promotion retrieved successfully", promotion);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<PromotionDTO> response = new ResponseWrapper<>("Promotion not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/promotions/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<Void>> deletePromotion(@PathVariable int id) {
        boolean isDeleted = promotionService.deleteById(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Promotion deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Promotion not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/promotions/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<PromotionDTO>> updatePromotion(@PathVariable int id, @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO updatedPromotion = promotionService.save(promotionDTO);
        if (updatedPromotion != null) {
            ResponseWrapper<PromotionDTO> response = new ResponseWrapper<>("Promotion updated successfully", updatedPromotion);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<PromotionDTO> response = new ResponseWrapper<>("Promotion not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
