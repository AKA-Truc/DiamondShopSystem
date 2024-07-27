package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.ProductName=:ProductName")
    public Product findByName(@Param("ProductName") String ProductName);
}
