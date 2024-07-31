package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.Status=:status")
    Cart findByStatus(@Param("status") String status);
}
