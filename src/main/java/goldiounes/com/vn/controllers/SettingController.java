package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.SettingDTO;
import goldiounes.com.vn.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setting-management")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/settings")
    public List<SettingDTO> getAllSettings() {
        return settingService.getAllSettings();
    }

    @GetMapping("/settings/{id}")
    public SettingDTO getSetting(@PathVariable int id) {
        return settingService.getSetting(id);
    }

    @PostMapping("/settings")
    public SettingDTO createSetting(@RequestBody SettingDTO setting) {
        return settingService.createSetting(setting);
    }

    @PutMapping("/settings/{id}")
    public SettingDTO updateSetting(@PathVariable int id, @RequestBody SettingDTO settingDTO) {
        return settingService.updateSetting(id, settingDTO);
    }

    @DeleteMapping("/settings/{id}")
    public void deleteSetting(@PathVariable int id) {
        settingService.deleteSetting(id);
    }
}