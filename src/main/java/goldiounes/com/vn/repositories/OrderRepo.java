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

    @Query("SELECT SUM(o.TotalPrice) AS revenue " +
            "FROM Order o " +
            "WHERE YEAR(o.StartDate) = :year AND MONTH(o.StartDate) = :month " +
            "GROUP BY MONTH(o.StartDate)")
    int findRevenueBySpecificMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT MONTH(o.StartDate) AS month, SUM(o.TotalPrice) AS revenue " +
            "FROM Order o " +
            "WHERE YEAR(o.StartDate) = :year " +
            "GROUP BY MONTH(o.StartDate) " +
            "ORDER BY MONTH(o.StartDate)")
    int findRevenueBySpecificYear(@Param("year") int year);

    @Query("SELECT FUNCTION('DATE', o.StartDate), COUNT(o) FROM Order o GROUP BY FUNCTION('DATE', o.StartDate)")
    List<Object[]> countOrdersByDate();

    @Query("SELECT FUNCTION('MONTH', o.StartDate), COUNT(o) FROM Order o GROUP BY FUNCTION('MONTH', o.StartDate)")
    List<Object[]> countOrdersByMonth();

    @Query("SELECT FUNCTION('YEAR', o.StartDate), COUNT(o) FROM Order o GROUP BY FUNCTION('YEAR', o.StartDate)")
    List<Object[]> countOrdersByYear();

}

