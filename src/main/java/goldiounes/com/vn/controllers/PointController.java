package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point-management")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/points")
    public ResponseEntity<ResponseWrapper<List<PointDTO>>> getAllPoints() {
        List<PointDTO> points = pointService.findAll();
        ResponseWrapper<List<PointDTO>> response = new ResponseWrapper<>("Points retrieved successfully", points);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/points/{id}")
    public ResponseEntity<ResponseWrapper<PointDTO>> getPointById(@PathVariable int id) {
        PointDTO point = pointService.findById(id);
        if (point != null) {
            ResponseWrapper<PointDTO> response = new ResponseWrapper<>("Point retrieved successfully", point);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<PointDTO> response = new ResponseWrapper<>("Point not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/points")
    public ResponseEntity<ResponseWrapper<PointDTO>> createPoint(@RequestBody PointDTO pointDTO) {
        PointDTO createdPoint = pointService.createPoint(pointDTO);
        ResponseWrapper<PointDTO> response = new ResponseWrapper<>("Point created successfully", createdPoint);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/points/{id}")
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
