package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Point;
import goldiounes.com.vn.models.User;
import goldiounes.com.vn.repositories.PointRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<User> userOptional = userRepo.findById(point.getUser().getUserID());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }
        point.setUser(userOptional.get());
        return pointRepo.save(point);
    }
    public void deleteById(int id) {
        pointRepo.deleteById(id);
    }

    public Point save(Point point) {
        return pointRepo.save(point);
    }
}
