package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Setting;
import goldiounes.com.vn.repositories.SettingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SettingService {
    @Autowired
    private SettingRepo settingRepo;

    public List<Setting> findAll() {
        return settingRepo.findAll();
    }
    public Setting findById(int id) {
        return settingRepo.findById(id).get();
    }
    public Setting save(Setting setting) {
        return settingRepo.save(setting);
    }
    public void deleteById(int id) {
        settingRepo.deleteById(id);
    }
}
