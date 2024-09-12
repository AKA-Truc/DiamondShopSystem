package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.DiamondDTO;
import goldiounes.com.vn.models.entities.Diamond;
import goldiounes.com.vn.repositories.DiamondRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiamondServiceTest {

    @Mock
    private DiamondRepo diamondRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DiamondService diamondService;

    private Diamond diamond;
    private DiamondDTO diamondDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        diamond = new Diamond();
        diamond.setDiamondID(1);
        diamond.setStatus("active");

        diamondDTO = new DiamondDTO();
        diamondDTO.setDiamondId(1);
    }

    @Test
    void findAll_activeDiamonds() {
        Diamond inactiveDiamond = new Diamond();
        inactiveDiamond.setStatus("inactive");

        Diamond activeDiamond = new Diamond();
        activeDiamond.setStatus("active");

        // List containing both active and inactive diamonds
        List<Diamond> diamonds = Arrays.asList(activeDiamond, inactiveDiamond);
        when(diamondRepo.findAll()).thenReturn(diamonds);

        // Mock the ModelMapper to map a list of active diamonds to DiamondDTOs
        List<DiamondDTO> diamondDTOList = Arrays.asList(new DiamondDTO()); // Simulated mapping result
        TypeToken<List<DiamondDTO>> typeToken = new TypeToken<List<DiamondDTO>>() {};

        when(modelMapper.map(
                ArgumentMatchers.anyList(),
                ArgumentMatchers.eq(typeToken.getType())
        )).thenReturn(diamondDTOList);

        // Call the service method
        List<DiamondDTO> result = diamondService.findAll();

        // Assert that the result is as expected
        assertNotNull(result); // Ensure the result is not null
        assertEquals(1, result.size()); // Expect only 1 active diamond in the result

        // Verify interactions with the repository and modelMapper
        verify(diamondRepo, times(1)).findAll();
        verify(modelMapper, times(1)).map(ArgumentMatchers.anyList(), ArgumentMatchers.eq(typeToken.getType()));
    }



    @Test
    void findById_diamondExists() {
        when(diamondRepo.findById(1)).thenReturn(Optional.of(diamond));
        when(modelMapper.map(diamond, DiamondDTO.class)).thenReturn(diamondDTO);

        DiamondDTO result = diamondService.findById(1);

        assertNotNull(result);
        verify(diamondRepo, times(1)).findById(1);
        verify(modelMapper, times(1)).map(diamond, DiamondDTO.class);
    }

    @Test
    void findById_diamondNotActive() {
        diamond.setStatus("inactive");
        when(diamondRepo.findById(1)).thenReturn(Optional.of(diamond));

        assertThrows(RuntimeException.class, () -> diamondService.findById(1));
        verify(diamondRepo, times(1)).findById(1);
    }

    @Test
    void createDiamond_diamondExists() {
        when(diamondRepo.findDiamond(diamondDTO.getCarat(), diamondDTO.getClarity(), diamondDTO.getColor(),
                diamondDTO.getCut(), diamondDTO.getShape(), diamondDTO.getMeasurement()))
                .thenReturn(diamond);

        assertThrows(RuntimeException.class, () -> diamondService.createDiamond(diamondDTO));
        verify(diamondRepo, times(1)).findDiamond(any(), any(), any(), any(), any(), any());
    }

    @Test
    void createDiamond_diamondDoesNotExist() {
        when(diamondRepo.findDiamond(diamondDTO.getCarat(), diamondDTO.getClarity(), diamondDTO.getColor(),
                diamondDTO.getCut(), diamondDTO.getShape(), diamondDTO.getMeasurement()))
                .thenReturn(null);
        when(modelMapper.map(diamondDTO, Diamond.class)).thenReturn(diamond);
        when(diamondRepo.save(diamond)).thenReturn(diamond);
        when(modelMapper.map(diamond, DiamondDTO.class)).thenReturn(diamondDTO);

        DiamondDTO result = diamondService.createDiamond(diamondDTO);

        assertNotNull(result);
        assertEquals(diamondDTO.getDiamondId(), result.getDiamondId());
        verify(diamondRepo, times(1)).save(diamond);
    }

    @Test
    void updateDiamond_diamondExists() {
        // Initialize the mock diamond object with required fields
        Diamond diamond = new Diamond();
        diamond.setDiamondID(1);
        diamond.setMeasurement(3.5);
        diamond.setCarat(1.5);
        diamond.setClarity("VS1");
        diamond.setColor("D");
        diamond.setCut("Excellent");
        diamond.setShape("Round");

        DiamondDTO diamondDTO = new DiamondDTO();
        diamondDTO.setDiamondId(1);
        diamondDTO.setMeasurement(3.5);
        diamondDTO.setCarat(1.5);
        diamondDTO.setClarity("VS1");
        diamondDTO.setColor("D");
        diamondDTO.setCut("Excellent");
        diamondDTO.setShape("Round");

        // Mock repository and modelMapper behavior
        when(diamondRepo.findById(1)).thenReturn(Optional.of(diamond));
        when(diamondRepo.save(diamond)).thenReturn(diamond);
        when(modelMapper.map(diamond, DiamondDTO.class)).thenReturn(diamondDTO);

        // Call the service method
        DiamondDTO result = diamondService.updateDiamond(1, diamondDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(diamondDTO.getDiamondId(), result.getDiamondId());

        // Verify interactions
        verify(diamondRepo, times(1)).findById(1);
        verify(diamondRepo, times(1)).save(diamond);
    }


    @Test
    void deleteDiamond_diamondExists() {
        when(diamondRepo.findById(1)).thenReturn(Optional.of(diamond));

        boolean result = diamondService.deleteDiamond(1);

        assertTrue(result);
        verify(diamondRepo, times(1)).findById(1);
        verify(diamondRepo, times(1)).save(diamond);
    }

    @Test
    void getDiamondByGIACode_diamondExists() {
        when(diamondRepo.findDiamondByGIACode("GIA123")).thenReturn(diamond);
        when(modelMapper.map(diamond, DiamondDTO.class)).thenReturn(diamondDTO);

        DiamondDTO result = diamondService.getDiamondByGIACode("GIA123");

        assertNotNull(result);
        verify(diamondRepo, times(1)).findDiamondByGIACode("GIA123");
        verify(modelMapper, times(1)).map(diamond, DiamondDTO.class);
    }
}
