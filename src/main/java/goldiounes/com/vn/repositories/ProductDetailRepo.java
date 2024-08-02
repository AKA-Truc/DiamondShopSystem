package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Integer> {

}