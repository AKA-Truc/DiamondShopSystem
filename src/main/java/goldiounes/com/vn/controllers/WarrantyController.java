package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.WarrantyDTO;
import goldiounes.com.vn.services.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warranty-management")
public class WarrantyController {

    @Autowired
    private WarrantyService warrantyService;

    @PostMapping("/warranties")
    public WarrantyDTO createWarranty(@RequestBody WarrantyDTO warrantyDTO) {
        return warrantyService.createWarranty(warrantyDTO);
    }

    @GetMapping("/warranties/{id}")
    public WarrantyDTO getWarranty(@PathVariable int id) {
        return warrantyService.getWarranty(id);
    }

    @GetMapping("/warranties")
    public List<WarrantyDTO> getAllWarranties() {
        return warrantyService.getAllWarranties();
    }

    @GetMapping("/warranties/{email}")
    public WarrantyDTO getWarrantyByEmail(@PathVariable String email) {
        return (WarrantyDTO) warrantyService.findByUserEmail(email);
    }

    @PutMapping("/warranties/{id}")
    public WarrantyDTO updateWarranty(@PathVariable int id, @RequestBody WarrantyDTO warrantyDTO) {
        return warrantyService.updateWarranty(id, warrantyDTO);
    }

    @DeleteMapping("/warranties/{id}")
    public void deleteWarranty(@PathVariable int id) {
        warrantyService.deleteWarranty(id);
    }
}
