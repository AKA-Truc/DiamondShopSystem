package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.models.entities.Point;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.PointRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {
    @Autowired
    private PointRepo pointRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<PointDTO> findAll() {
        List<Point> points = pointRepo.findAll();
        if (points.isEmpty()) {
            throw new RuntimeException("No points found");
        } else {
            return modelMapper.map(points, new TypeToken<List<PointDTO>>() {}.getType());
        }
    }

    public PointDTO findById(int id) {
        Point existingPoint = pointRepo.findById(id).orElseThrow(() -> new RuntimeException("No point found"));
        return modelMapper.map(existingPoint, new TypeToken<PointDTO>() {}.getType());
    }

    public PointDTO createPoint(PointDTO pointDTO) {
        Point point = modelMapper.map(pointDTO, Point.class);
        User existingUser = userRepo.findById(point.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("No User found"));
        point.setUser(existingUser);
        pointRepo.save(point);
        return modelMapper.map(point, new TypeToken<PointDTO>() {}.getType());
    }

    public boolean deleteById(int id) {
        Point existingPoint = pointRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No points found"));
        pointRepo.deleteById(existingPoint.getUser().getUserID());
        return true;
    }

    public PointDTO updatePoint(int id, PointDTO pointDTO) {
        Point point = modelMapper.map(pointDTO, Point.class);
        Point existingPoint = pointRepo.findById(id).orElseThrow(() -> new RuntimeException("No point found"));
        User existingUser = userRepo.findById(existingPoint.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("No User found"));
        existingPoint.setUser(existingUser);
        existingPoint.setPoints(point.getPoints());
        return modelMapper.map(pointRepo.save(existingPoint), PointDTO.class);
    }
}