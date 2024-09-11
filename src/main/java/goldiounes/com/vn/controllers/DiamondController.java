package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.DiamondDTO;
import goldiounes.com.vn.models.dtos.DiamondDetailDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.DiamondDetailService;
import goldiounes.com.vn.services.DiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diamond-management")
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private DiamondDetailService diamondDetailService;

    @PostMapping("/diamonds")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<DiamondDTO>> createDiamond(@RequestBody DiamondDTO diamondDTO) {
        DiamondDTO createdDiamond = diamondService.createDiamond(diamondDTO);
        ResponseWrapper<DiamondDTO> response = new ResponseWrapper<>("Diamond created successfully", createdDiamond);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/diamonds")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<List<DiamondDTO>>> getAllDiamonds() {
        List<DiamondDTO> diamonds = diamondService.findAll();
        ResponseWrapper<List<DiamondDTO>> response;

        if (!diamonds.isEmpty()) {
            response = new ResponseWrapper<>("Diamonds retrieved successfully", diamonds);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No diamonds found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/diamonds/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<DiamondDTO>> getDiamond(@PathVariable int id) {
        DiamondDTO diamond = diamondService.findById(id);
        ResponseWrapper<DiamondDTO> response;

        if (diamond != null) {
            response = new ResponseWrapper<>("Diamond retrieved successfully", diamond);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Diamond not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/diamonds/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<DiamondDTO>> updateDiamond(@PathVariable int id, @RequestBody DiamondDTO diamondDTO) {
        DiamondDTO updatedDiamond = diamondService.updateDiamond(id, diamondDTO);
        ResponseWrapper<DiamondDTO> response;

        if (updatedDiamond != null) {
            response = new ResponseWrapper<>("Diamond updated successfully", updatedDiamond);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Diamond not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/diamonds/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteDiamond(@PathVariable int id) {
        boolean isDeleted = diamondService.deleteDiamond(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Diamond deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Diamond not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/diamond-details")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<DiamondDetailDTO>> createDiamondDetail(@RequestBody DiamondDetailDTO diamondDetailDTO) {
        DiamondDetailDTO createdDetail = diamondDetailService.createDiamondDetail(diamondDetailDTO);
        ResponseWrapper<DiamondDetailDTO> response = new ResponseWrapper<>("Diamond detail created successfully", createdDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/diamond-details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<List<DiamondDetailDTO>>> getAllDiamondDetails() {
        List<DiamondDetailDTO> diamondDetails = diamondDetailService.findAll();
        ResponseWrapper<List<DiamondDetailDTO>> response;

        if (!diamondDetails.isEmpty()) {
            response = new ResponseWrapper<>("Diamond details retrieved successfully", diamondDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No diamond details found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/diamondGIACODE/{giaCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<DiamondDTO>> getDiamondByGIACode(@PathVariable("giaCode") String giaCode) {
        DiamondDTO existingDiamond = diamondService.getDiamondByGIACode(giaCode);
        ResponseWrapper<DiamondDTO> response;
        if (existingDiamond != null) {
            response =  new ResponseWrapper<>("Diamond retrieved successfully", existingDiamond);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No diamond details found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/diamond-details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER', 'ROLE_SALE STAFF')")
    public ResponseEntity<ResponseWrapper<DiamondDetailDTO>> getDiamondDetail(@PathVariable int id) {
        DiamondDetailDTO diamondDetail = diamondDetailService.findById(id);
        ResponseWrapper<DiamondDetailDTO> response;

        if (diamondDetail != null) {
            response = new ResponseWrapper<>("Diamond detail retrieved successfully", diamondDetail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Diamond detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/diamond-details/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<DiamondDetailDTO>> updateDiamondDetail(@PathVariable int id, @RequestBody DiamondDetailDTO diamondDetailDTO) {
        DiamondDetailDTO updatedDetail = diamondDetailService.update(id, diamondDetailDTO);
        ResponseWrapper<DiamondDetailDTO> response;

        if (updatedDetail != null) {
            response = new ResponseWrapper<>("Diamond detail updated successfully", updatedDetail);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Diamond detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/diamond-details/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteDiamondDetail(@PathVariable int id) {
        boolean isDeleted = diamondDetailService.deleteById(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Diamond detail deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Diamond detail not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
