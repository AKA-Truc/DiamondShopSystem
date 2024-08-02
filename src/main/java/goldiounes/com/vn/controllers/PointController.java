package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Point;
import goldiounes.com.vn.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point-management")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/points")
    public Point createPoint(@RequestBody Point point) {
        return pointService.createPoint(point);
    }

    @GetMapping("/points")
    public List<Point> getAllPoints() {
        List<Point> points = pointService.findAll();
        if (points.isEmpty()) {
            throw new RuntimeException("No points found");
        }
        return points;
    }

    @GetMapping("/points/{id}")
    public Point getPoint(@PathVariable int id) {
        Point existingPoint = pointService.findById(id);
        if (existingPoint == null) {
            throw new RuntimeException("Point not found");
        }
        return existingPoint;
    }

    @DeleteMapping("/points/{id}")
    public void deletePoint(@PathVariable int id) {
        Point existingPoint = pointService.findById(id);
        if (existingPoint == null) {
            throw new RuntimeException("Point not found");
        }
        pointService.deleteById(existingPoint.getPointID());
    }

    @PutMapping("/points/{id}")
    public Point updatePoint(@PathVariable int id, @RequestBody Point point) {
        Point existingPoint = pointService.findById(id);
        if (existingPoint == null) {
            throw new RuntimeException("Point not found");
        }
        existingPoint.setPoints(point.getPoints());
        existingPoint.setUser(point.getUser());
        return pointService.save(existingPoint);
    }
}
