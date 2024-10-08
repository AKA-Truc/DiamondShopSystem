package goldiounes.com.vn.controllers;

import goldiounes.com.vn.config.CustomUserDetails;
import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point-management")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/points")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<List<PointDTO>>> getAllPoints() {
        List<PointDTO> points = pointService.findAll();
        ResponseWrapper<List<PointDTO>> response = new ResponseWrapper<>("Points retrieved successfully", points);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/points/{id}")
    @PreAuthorize(
            "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STAFF') or " +
                    "(hasAuthority('ROLE_CUSTOMER') and #id == #authentication.principal.id)"
    )
    public ResponseEntity<ResponseWrapper<PointDTO>> getPointById(@PathVariable int id, Authentication authentication) {
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        PointDTO point = pointService.findById(id);

        if (point == null) {
            return new ResponseEntity<>(new ResponseWrapper<>("Point not found", null), HttpStatus.NOT_FOUND);
        }

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (point.getUser().getUserId() != currentUser.getId()) {
                return new ResponseEntity<>(new ResponseWrapper<>("Access denied", null), HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>(new ResponseWrapper<>("Point retrieved successfully", point), HttpStatus.OK);
    }

    @PostMapping("/points")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<PointDTO>> createPoint(@RequestBody PointDTO pointDTO) {
        PointDTO createdPoint = pointService.createPoint(pointDTO);
        ResponseWrapper<PointDTO> response = new ResponseWrapper<>("Point created successfully", createdPoint);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/points/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<PointDTO>> updatePoint(@PathVariable int id, @RequestBody PointDTO pointDTO) {
        PointDTO updatedPoint = pointService.updatePoint(id, pointDTO);
        if (updatedPoint != null) {
            ResponseWrapper<PointDTO> response = new ResponseWrapper<>("Point updated successfully", updatedPoint);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<PointDTO> response = new ResponseWrapper<>("Point not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/points/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<Void>> deletePoint(@PathVariable int id) {
        boolean isDeleted = pointService.deleteById(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("Point deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Point not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
