package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.SettingDTO;
import goldiounes.com.vn.models.entities.Setting;
import goldiounes.com.vn.repositories.SettingRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Setting existingSetting = settingRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No setting found"));
        return modelMapper.map(existingSetting,SettingDTO.class);
    }

    public SettingDTO createSetting(SettingDTO settingDTO) {
        Setting setting = modelMapper.map(settingDTO, Setting.class);
        settingRepo.save(setting);
        return modelMapper.map(setting, SettingDTO.class);
    }

    public SettingDTO updateSetting(int id, SettingDTO settingDTO) {
        Setting setting = modelMapper.map(settingDTO, Setting.class);
        Setting existingSetting = settingRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No setting found"));

        existingSetting.setMaterial(setting.getMaterial());
        existingSetting.setPrice(setting.getPrice());
        settingRepo.save(existingSetting);
        return modelMapper.map(existingSetting, SettingDTO.class);
    }

    public boolean deleteSetting(int id) {
        Setting existingSetting = settingRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No setting found"));
        settingRepo.deleteById(existingSetting.getSettingID());
        return true;
    }
}
