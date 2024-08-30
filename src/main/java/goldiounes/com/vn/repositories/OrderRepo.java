package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.User.UserID = :userId")
    List<Order> findByUserId(@Param("userId") int userId);

    @Query("SELECT MONTH(o.StartDate) AS month, SUM(o.TotalPrice) AS revenue " +
            "FROM Order o " +
            "WHERE o.StartDate BETWEEN :startDate AND :endDate " +
            "GROUP BY MONTH(o.StartDate) " +
            "ORDER BY MONTH(o.StartDate)")
    List<Object[]> findRevenueByMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
