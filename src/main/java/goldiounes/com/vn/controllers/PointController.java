package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.PointDTO;
import goldiounes.com.vn.models.entity.Point;
import goldiounes.com.vn.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point-management")
public class PointController {

    @Autowired
    private PointService pointService;

    @GetMapping("/points")
    public List<PointDTO> getAllPoints() {
        return pointService.findAll();
    }

    @GetMapping("/points")
    public PointDTO getPointById(@PathVariable int id) {
        return pointService.findById(id);
    }

    @PostMapping("/points")
    public PointDTO createPoint(@RequestBody Point point) {
        return pointService.createpoint(point);
    }

    @PutMapping("/points/{id}")
    public PointDTO updatePoint(@PathVariable int id, @RequestBody PointDTO pointDTO) {
        return pointService.updatePoint(id,pointDTO);
    }

    @DeleteMapping("/points/{id}")
    public void deletePoint(@PathVariable int id) {
        pointService.deleteById(id);
    }
}



