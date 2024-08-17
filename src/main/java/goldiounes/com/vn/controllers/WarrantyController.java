package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.WarrantyDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warranty-management")
public class WarrantyController {

    @Autowired
    private WarrantyService warrantyService;

    @PostMapping("/warranties")
    public ResponseEntity<ResponseWrapper<WarrantyDTO>> createWarranty(@PathVariable int orderId, @RequestBody WarrantyDTO warrantyDTO) {
        WarrantyDTO createdWarranty = warrantyService.createWarranty(orderId, warrantyDTO);
        ResponseWrapper<WarrantyDTO> response = new ResponseWrapper<>("Warranty created successfully", createdWarranty);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/warranties/{id}")
    public ResponseEntity<ResponseWrapper<WarrantyDTO>> getWarranty(@PathVariable int id) {
        WarrantyDTO warranty = warrantyService.getWarranty(id);
        if (warranty != null) {
            ResponseWrapper<WarrantyDTO> response = new ResponseWrapper<>("Warranty retrieved successfully", warranty);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<WarrantyDTO> response = new ResponseWrapper<>("Warranty not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/warranties")
    public ResponseEntity<ResponseWrapper<List<WarrantyDTO>>> getAllWarranties() {
        List<WarrantyDTO> warranties = warrantyService.getAllWarranties();
        ResponseWrapper<List<WarrantyDTO>> response = new ResponseWrapper<>("Warranties retrieved successfully", warranties);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/warranties/user/{email}")
    public ResponseEntity<ResponseWrapper<List<WarrantyDTO>>> getWarrantyByEmail(@PathVariable String email) {
        List<WarrantyDTO> warranty = warrantyService.findByUserEmail(email);
        if (!warranty.isEmpty()) {
            ResponseWrapper<List<WarrantyDTO>> response = new ResponseWrapper<>("Warranty retrieved successfully", warranty);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<WarrantyDTO>> response = new ResponseWrapper<>("Warranty not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/warranties/{id}")
    public ResponseEntity<ResponseWrapper<WarrantyDTO>> updateWarranty(@PathVariable int id, @RequestBody WarrantyDTO warrantyDTO) {
        WarrantyDTO updatedWarranty = warrantyService.updateWarranty(id, warrantyDTO);
        if (updatedWarranty != null) {
            ResponseWrapper<WarrantyDTO> response = new ResponseWrapper<>("Warranty updated successfully", updatedWarranty);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<WarrantyDTO> response = new ResponseWrapper<>("Warranty not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/warranties/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteWarranty(@PathVariable int id) {
        boolean isDeleted = warrantyService.deleteWarranty(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Warranty deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Warranty not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
