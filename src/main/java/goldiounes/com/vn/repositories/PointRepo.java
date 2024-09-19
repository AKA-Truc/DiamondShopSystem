package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepo extends JpaRepository<Point, Integer> {

}
