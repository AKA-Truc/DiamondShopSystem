package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.SettingDTO;
import goldiounes.com.vn.models.entities.Setting;
import goldiounes.com.vn.repositories.SettingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingServiceTest {

    @Mock
    private SettingRepo settingRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SettingService settingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSettings_SettingsExist_ReturnsListOfSettingDTOs() {
        // Arrange
        Setting setting = new Setting();
        setting.setMaterial("Wood");
        setting.setPrice(100);

        SettingDTO settingDTO = new SettingDTO();
        settingDTO.setMaterial("Wood");
        settingDTO.setPrice(100);

        List<Setting> settings = Arrays.asList(setting);
        List<SettingDTO> settingDTOs = Arrays.asList(settingDTO);

        when(settingRepo.findAll()).thenReturn(settings);
        when(modelMapper.map(settings, new TypeToken<List<SettingDTO>>() {}.getType())).thenReturn(settingDTOs);

        // Act
        List<SettingDTO> result = settingService.getAllSettings();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Wood", result.get(0).getMaterial());
        verify(settingRepo).findAll();
    }

    @Test
    void getAllSettings_NoSettingsFound_ThrowsRuntimeException() {
        // Arrange
        when(settingRepo.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> settingService.getAllSettings());
        verify(settingRepo).findAll();
    }

    @Test
    void getSetting_ExistingSetting_ReturnsSettingDTO() {
        // Arrange
        int settingId = 1;
        Setting setting = new Setting();
        setting.setMaterial("Wood");
        setting.setPrice(100);

        SettingDTO settingDTO = new SettingDTO();
        settingDTO.setMaterial("Wood");
        settingDTO.setPrice(100);

        when(settingRepo.findById(settingId)).thenReturn(Optional.of(setting));
        when(modelMapper.map(setting, SettingDTO.class)).thenReturn(settingDTO);

        // Act
        SettingDTO result = settingService.getSetting(settingId);

        // Assert
        assertNotNull(result);
        assertEquals("Wood", result.getMaterial());
        verify(settingRepo).findById(settingId);
    }

    @Test
    void getSetting_NonExistingSetting_ThrowsRuntimeException() {
        // Arrange
        int settingId = 1;
        when(settingRepo.findById(settingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> settingService.getSetting(settingId));
        verify(settingRepo).findById(settingId);
    }

    @Test
    void createSetting_ValidSettingDTO_ReturnsCreatedSettingDTO() {
        // Arrange
        SettingDTO settingDTO = new SettingDTO();
        settingDTO.setMaterial("Wood");
        settingDTO.setPrice(100);

        Setting setting = new Setting();
        setting.setMaterial("Wood");
        setting.setPrice(100);

        when(modelMapper.map(settingDTO, Setting.class)).thenReturn(setting);
        when(settingRepo.save(setting)).thenReturn(setting);
        when(modelMapper.map(setting, SettingDTO.class)).thenReturn(settingDTO);

        // Act
        SettingDTO result = settingService.createSetting(settingDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Wood", result.getMaterial());
        verify(modelMapper).map(settingDTO, Setting.class);
        verify(settingRepo).save(setting);
        verify(modelMapper).map(setting, SettingDTO.class);
    }

    @Test
    void updateSetting_ExistingSetting_ReturnsUpdatedSettingDTO() {
        // Arrange
        int settingId = 1;
        SettingDTO settingDTO = new SettingDTO();
        settingDTO.setMaterial("Metal");
        settingDTO.setPrice(150);

        Setting existingSetting = new Setting();
        existingSetting.setMaterial("Wood");
        existingSetting.setPrice(100);

        Setting updatedSetting = new Setting();
        updatedSetting.setMaterial("Metal");
        updatedSetting.setPrice(150);

        when(settingRepo.findById(settingId)).thenReturn(Optional.of(existingSetting));
        when(modelMapper.map(settingDTO, Setting.class)).thenReturn(updatedSetting);
        when(settingRepo.save(existingSetting)).thenReturn(updatedSetting);
        when(modelMapper.map(updatedSetting, SettingDTO.class)).thenReturn(settingDTO);

        // Act
        SettingDTO result = settingService.updateSetting(settingId, settingDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Metal", result.getMaterial());
        assertEquals(150, result.getPrice());
        verify(settingRepo).findById(settingId);
        verify(settingRepo).save(existingSetting);
        verify(modelMapper).map(settingDTO, Setting.class);
        verify(modelMapper).map(updatedSetting, SettingDTO.class);
    }

    @Test
    void updateSetting_NonExistingSetting_ThrowsRuntimeException() {
        // Arrange
        int settingId = 1;
        SettingDTO settingDTO = new SettingDTO();
        settingDTO.setMaterial("Metal");
        settingDTO.setPrice(150);

        when(settingRepo.findById(settingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> settingService.updateSetting(settingId, settingDTO));
        verify(settingRepo).findById(settingId);
        verify(settingRepo, never()).save(any(Setting.class));
    }

    @Test
    void deleteSetting_ExistingSetting_ReturnsTrue() {
        // Arrange
        int settingId = 1;
        Setting setting = new Setting();
        setting.setSettingID(settingId);

        when(settingRepo.findById(settingId)).thenReturn(Optional.of(setting));

        // Act
        boolean result = settingService.deleteSetting(settingId);

        // Assert
        assertTrue(result);
        verify(settingRepo).deleteById(settingId);
    }

    @Test
    void deleteSetting_NonExistingSetting_ThrowsRuntimeException() {
        // Arrange
        int settingId = 1;
        when(settingRepo.findById(settingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> settingService.deleteSetting(settingId));
        verify(settingRepo).findById(settingId);
        verify(settingRepo, never()).deleteById(settingId);
    }
}
