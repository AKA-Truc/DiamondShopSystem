package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepo  extends JpaRepository<Promotion, Integer> {
}
