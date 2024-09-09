package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.User.UserID = :userId")
    List<Order> findByUserId(@Param("userId") int userId);

    // Truy vấn tổng doanh thu theo tháng và năm
    @Query("SELECT SUM(o.TotalPrice) FROM Order o " +
            "WHERE EXTRACT(YEAR FROM o.StartDate) = :year " +
            "AND EXTRACT(MONTH FROM o.StartDate) = :month")
    Long findRevenueBySpecificMonth(@Param("year") int year, @Param("month") int month);

    // Truy vấn tổng doanh thu theo năm bằng chart
    @Query("SELECT EXTRACT(MONTH FROM o.StartDate) AS month, SUM(o.TotalPrice) AS totalRevenue " +
            "FROM Order o " +
            "WHERE EXTRACT(YEAR FROM o.StartDate) = :year " +
            "GROUP BY EXTRACT(MONTH FROM o.StartDate) " +
            "ORDER BY EXTRACT(MONTH FROM o.StartDate)")
    List<Object[]> findMonthlyRevenue(@Param("year") int year);


    // Đếm đơn hàng hôm nay
    @Query("SELECT COUNT(o) FROM Order o WHERE o.StartDate = CURRENT_DATE")
    Long countOrdersByToday();

    // Đếm tổng số đơn hàng
    @Query("SELECT COUNT(o) FROM Order o")
    Long countOrder();

    // Đếm số đơn hàng theo năm
    @Query("SELECT EXTRACT(MONTH FROM o.StartDate) AS month, COUNT(o) FROM Order o " +
            "WHERE EXTRACT(YEAR FROM o.StartDate) = :year " +
            "GROUP BY EXTRACT(MONTH FROM o.StartDate) " +
            "ORDER BY EXTRACT(MONTH FROM o.StartDate)")
    List<Object[]> countOrdersByMonthInYear(@Param("year") int year);

}

