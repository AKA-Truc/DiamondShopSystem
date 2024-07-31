package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.DiamondDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiamondDetailRepo extends JpaRepository<DiamondDetail, Integer> {
}
