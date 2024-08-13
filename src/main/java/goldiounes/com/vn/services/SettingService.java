package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.SettingDTO;
import goldiounes.com.vn.models.entities.Setting;
import goldiounes.com.vn.repositories.SettingRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingService {

    @Autowired
    private SettingRepo settingRepo;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ModelMapper modelMapper;

    public List<SettingDTO> getAllSettings() {
        List<Setting> settings = settingRepo.findAll();
        if (settings.isEmpty()) {
            throw new RuntimeException("No settings found");
        }
        return modelMapper.map(settings, new TypeToken<List<SettingDTO>>() {}.getType());
    }

    public SettingDTO getSetting(int id) {
        Setting existingSetting = settingRepo.findById(id).get();
        if (existingSetting == null) {
            throw new RuntimeException("No setting found ");
        }
        return modelMapper.map(existingSetting,new TypeToken<SettingDTO>() {}.getType());
    }

    public SettingDTO createSetting(SettingDTO settingDTO) {
        Optional<Setting> existingSetting = settingRepo.findById(settingDTO.getSettingID());
        if (existingSetting.isPresent()) {
            throw new RuntimeException("Setting already exists");
        }
        Setting setting = modelMapper.map(settingDTO, Setting.class);
        settingRepo.save(setting);
        return modelMapper.map(existingSetting, SettingDTO.class);
    }

    public SettingDTO updateSetting(int id, SettingDTO settingDTO) {
        Setting existingSetting = settingRepo.findById(id).get();
        if (existingSetting == null) {
            throw new RuntimeException("No setting found ");
        }

        existingSetting.setMaterial(settingDTO.getMaterial());
        existingSetting.setPrice(settingDTO.getPrice());
        settingRepo.save(existingSetting);
        return modelMapper.map(existingSetting, SettingDTO.class);
    }

    public void deleteSetting(int id) {
        Setting existingSetting = settingRepo.findById(id).get();
        if (existingSetting == null) {
            throw new RuntimeException("No setting found ");
        }
        settingRepo.delete(existingSetting);
    }
}
