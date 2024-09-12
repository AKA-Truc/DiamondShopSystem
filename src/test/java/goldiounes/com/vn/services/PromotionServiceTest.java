package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.PromotionDTO;
import goldiounes.com.vn.models.entities.Promotion;
import goldiounes.com.vn.repositories.PromotionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceTest {

    @Mock
    private PromotionRepo promotionRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_PromotionsExist_ReturnsListOfPromotionDTOs() {
        // Arrange
        Promotion promotion = new Promotion();
        promotion.setPromotionName("Discount");
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionName("Discount");

        List<Promotion> promotions = Arrays.asList(promotion);
        List<PromotionDTO> promotionDTOs = Arrays.asList(promotionDTO);

        when(promotionRepo.findAll()).thenReturn(promotions);
        when(modelMapper.map(promotion, PromotionDTO.class)).thenReturn(promotionDTO);

        // Act
        List<PromotionDTO> result = promotionService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Discount", result.get(0).getPromotionName());
        verify(promotionRepo).findAll();
    }

    @Test
    void findAll_NoPromotionsFound_ThrowsRuntimeException() {
        // Arrange
        when(promotionRepo.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> promotionService.findAll());
        verify(promotionRepo).findAll();
    }

    @Test
    void findById_ExistingPromotion_ReturnsPromotionDTO() {
        // Arrange
        int promotionId = 1;
        Promotion promotion = new Promotion();
        promotion.setPromotionName("Discount");
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionName("Discount");

        when(promotionRepo.findById(promotionId)).thenReturn(Optional.of(promotion));
        when(modelMapper.map(promotion, PromotionDTO.class)).thenReturn(promotionDTO);

        // Act
        PromotionDTO result = promotionService.findById(promotionId);

        // Assert
        assertNotNull(result);
        assertEquals("Discount", result.getPromotionName());
        verify(promotionRepo).findById(promotionId);
    }

    @Test
    void findById_NonExistingPromotion_ThrowsRuntimeException() {
        // Arrange
        int promotionId = 1;
        when(promotionRepo.findById(promotionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> promotionService.findById(promotionId));
        verify(promotionRepo).findById(promotionId);
    }

    @Test
    void save_ValidPromotionDTO_ReturnsSavedPromotionDTO() {
        // Arrange
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionName("Discount");

        Promotion promotion = new Promotion();
        promotion.setPromotionName("Discount");

        when(modelMapper.map(promotionDTO, Promotion.class)).thenReturn(promotion);
        when(promotionRepo.save(promotion)).thenReturn(promotion);
        when(modelMapper.map(promotion, PromotionDTO.class)).thenReturn(promotionDTO);

        // Act
        PromotionDTO result = promotionService.save(promotionDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Discount", result.getPromotionName());
        verify(modelMapper).map(promotionDTO, Promotion.class);
        verify(promotionRepo).save(promotion);
        verify(modelMapper).map(promotion, PromotionDTO.class);
    }

    @Test
    void deleteById_ExistingPromotion_ReturnsTrue() {
        // Arrange
        int promotionId = 1;
        when(promotionRepo.existsById(promotionId)).thenReturn(true);

        // Act
        boolean result = promotionService.deleteById(promotionId);

        // Assert
        assertTrue(result);
        verify(promotionRepo).deleteById(promotionId);
    }

    @Test
    void deleteById_NonExistingPromotion_ThrowsRuntimeException() {
        // Arrange
        int promotionId = 1;
        when(promotionRepo.existsById(promotionId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> promotionService.deleteById(promotionId));
        verify(promotionRepo).existsById(promotionId);
        verify(promotionRepo, never()).deleteById(promotionId);
    }

    @Test
    void update_ExistingPromotion_ReturnsUpdatedPromotionDTO() {
        // Arrange
        int promotionId = 1;
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionName("Updated Discount");

        Promotion existingPromotion = new Promotion();
        existingPromotion.setPromotionName("Old Discount");

        Promotion updatedPromotion = new Promotion();
        updatedPromotion.setPromotionName("Updated Discount");

        when(promotionRepo.findById(promotionId)).thenReturn(Optional.of(existingPromotion));
        when(modelMapper.map(promotionDTO, Promotion.class)).thenReturn(updatedPromotion);
        when(promotionRepo.save(existingPromotion)).thenReturn(updatedPromotion);
        when(modelMapper.map(updatedPromotion, PromotionDTO.class)).thenReturn(promotionDTO);

        // Act
        PromotionDTO result = promotionService.update(promotionId, promotionDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Discount", result.getPromotionName());
        verify(promotionRepo).findById(promotionId);
        verify(promotionRepo).save(existingPromotion);
        verify(modelMapper).map(promotionDTO, Promotion.class);
        verify(modelMapper).map(updatedPromotion, PromotionDTO.class);
    }

    @Test
    void update_NonExistingPromotion_ThrowsRuntimeException() {
        // Arrange
        int promotionId = 1;
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setPromotionName("Updated Discount");

        when(promotionRepo.findById(promotionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> promotionService.update(promotionId, promotionDTO));
        verify(promotionRepo).findById(promotionId);
        verify(promotionRepo, never()).save(any(Promotion.class));
    }
}
