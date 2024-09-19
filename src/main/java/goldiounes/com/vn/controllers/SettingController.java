package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.SettingDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setting-management")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/settings")
    public ResponseEntity<ResponseWrapper<List<SettingDTO>>> getAllSettings() {
        List<SettingDTO> settings = settingService.getAllSettings();
        ResponseWrapper<List<SettingDTO>> response = new ResponseWrapper<>("Settings retrieved successfully", settings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/settings/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<SettingDTO>> getSetting(@PathVariable int id) {
        SettingDTO setting = settingService.getSetting(id);
        if (setting != null) {
            ResponseWrapper<SettingDTO> response = new ResponseWrapper<>("Setting retrieved successfully", setting);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<SettingDTO> response = new ResponseWrapper<>("Setting not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/settings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<SettingDTO>> createSetting(@RequestBody SettingDTO setting) {
        SettingDTO createdSetting = settingService.createSetting(setting);
        ResponseWrapper<SettingDTO> response = new ResponseWrapper<>("Setting created successfully", createdSetting);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/settings/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<SettingDTO>> updateSetting(@PathVariable int id, @RequestBody SettingDTO settingDTO) {
        SettingDTO updatedSetting = settingService.updateSetting(id, settingDTO);
        if (updatedSetting != null) {
            ResponseWrapper<SettingDTO> response = new ResponseWrapper<>("Setting updated successfully", updatedSetting);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<SettingDTO> response = new ResponseWrapper<>("Setting not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/settings/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteSetting(@PathVariable int id) {
        boolean isDeleted = settingService.deleteSetting(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Setting deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Setting not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
