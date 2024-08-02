package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Setting;
import goldiounes.com.vn.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("Setting")
public class SettingController {
    @Autowired
    private SettingService settingService;
    @PostMapping("/settings")
    public Setting createSetting(Setting setting) {
        Setting existingSetting = settingService.findById(setting.getSettingID());
        if (existingSetting != null) {
            throw new IllegalArgumentException("Setting already exists!");
        }
        return settingService.save(setting);
    }
    @PutMapping("/settings/{id}")
    public Setting updateSetting(@RequestBody Setting setting, @PathVariable int id) {
        Setting existingSetting = settingService.findById(setting.getSettingID());
        if (existingSetting == null) {
            throw new IllegalArgumentException("Setting does not exist!");
        }
        existingSetting.setMaterial(setting.getMaterial());
        existingSetting.setPrice(setting.getPrice());
        existingSetting.setProductDetails(setting.getProductDetails());
        return settingService.save(existingSetting);
    }
    @DeleteMapping("/settings/{id}")
    public void deleteSetting( @PathVariable int id)  {
        Setting existingSetting = settingService.findById(id);
        if (existingSetting == null) {
            throw new IllegalArgumentException("Setting does not exist!");
        }
        settingService.deleteById(existingSetting.getSettingID());
    }
    @GetMapping("/settings")
    public List<Setting> getSettings() {
        List<Setting> settings = settingService.findAll();
        if (settings.isEmpty()) {
            throw new IllegalArgumentException("No settings found!");
        }
        return settings;
    }
    @GetMapping(("/settings/{id}"))
    public Setting getSettingById( @PathVariable int id) {
        Setting existingsetting = settingService.findById(id);
        if (existingsetting == null) {
            throw new IllegalArgumentException("Setting does not exist!");
        }
        return existingsetting;
    }
}
