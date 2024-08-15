package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Integer> {

    @Query("select p from ProductDetail p where p.product.ProductID=:productId")
    List<ProductDetail> findByProductId(@Param("productId") int productId);

    @Query("select p from ProductDetail p where p.Size=:size and p.product.ProductID=:productId")
    ProductDetail findBySizeAndProductId(@Param("size") Integer size, @Param("productId") int productId);
}
