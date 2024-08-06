package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entity.DiamondDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiamondDetailRepo extends JpaRepository<DiamondDetail, Integer> {
    List<DiamondDetail> findByDiamondId(int diamondId);
}
