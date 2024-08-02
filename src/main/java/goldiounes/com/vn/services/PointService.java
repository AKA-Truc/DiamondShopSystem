package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Point;
import goldiounes.com.vn.models.User;
import goldiounes.com.vn.repositories.PointRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    @Autowired
    private PointRepo pointRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Point> findAll() {
        return pointRepo.findAll();
    }
    public Point findById(int id) {
        return pointRepo.findById(id).get();
    }
    public Point createPoint(Point point) {
        User user = userRepo.findById(point.getUser().getUserID()).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        point.setUser(user);
        return pointRepo.save(point);
    }
    public void deleteById(int id) {
        pointRepo.deleteById(id);
    }

    public Point save(Point point) {
        return pointRepo.save(point);
    }
}
