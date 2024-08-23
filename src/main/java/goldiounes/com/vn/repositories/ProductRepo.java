package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.ProductName=:ProductName")
    Product findByProductName(@Param("ProductName") String ProductName);

    @Query("select p from Product p where  p.Category.categoryName Like %:KeyWord%")
    List<Product> findByCategoryKeyWord(@Param("KeyWord") String KeyWord);

//    @Query("SELECT DISTINCT p FROM Product p JOIN p.ProductDetails pd WHERE p.Category.categoryName Like %:keyWord AND p.Status = 'active' AND pd.sellingPrice BETWEEN :minPrice AND :maxPrice")
//    List<Product> findActiveProductsByCategoryAndPrice(@Param("keyWord") String keyWord, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);
}
