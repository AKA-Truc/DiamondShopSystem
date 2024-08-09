package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.PointDTO;
import goldiounes.com.vn.models.entity.Point;
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
            return modelMapper.map(points, new TypeToken<List<PointDTO>>() {
            }.getType());
        }
    }

    public PointDTO findById(int id) {
        Point existingPoint = pointRepo.findById(id).orElseThrow(() -> new RuntimeException("No point found"));
        return modelMapper.map(existingPoint, new TypeToken<PointDTO>() {}.getType());
    }

    public PointDTO createpoint(Point point) {
        Point newpoint = modelMapper.map(pointRepo.saveAndFlush(point), Point.class);
        return modelMapper.map(point, new TypeToken<PointDTO>() {
        }.getType());
    }

    public void deleteById(int id) {
        Point existingPoint = pointRepo.findById(id).orElseThrow(() -> new RuntimeException("No points found"));
        pointRepo.deleteById(id);
    }

    public PointDTO updatePoint(int id, PointDTO pointDTO) {
        Point existingPoint = pointRepo.findById(id).get();
        if (existingPoint == null) {
            throw new RuntimeException("Point not found");
        }
        pointRepo.save(existingPoint);
        return modelMapper.map(pointRepo.save(existingPoint), PointDTO.class);
    }
}