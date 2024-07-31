package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Point;
import goldiounes.com.vn.repositories.PointRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    @Autowired
    private PointRepo pointRepo;

    public List<Point> findAll() {
        return pointRepo.findAll();
    }
    public Point findById(int id) {
        return pointRepo.findById(id).get();
    }
    public Point save(Point point) {
        return pointRepo.save(point);
    }
    public void deleteById(int id) {
        pointRepo.deleteById(id);
    }
}
