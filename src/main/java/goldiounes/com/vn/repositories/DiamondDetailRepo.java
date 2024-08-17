package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.DiamondDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiamondDetailRepo extends JpaRepository<DiamondDetail, Integer> {
}
