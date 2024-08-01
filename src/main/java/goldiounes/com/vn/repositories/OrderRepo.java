package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.User.UserID = :userId")
    List<Order> findByUserId(@Param("userId") int userId);
}
