package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {

    @Query("select od from OrderDetail od where od.Order.OrderID = :orderID")
    List<OrderDetail> findByOrderId(@Param("orderID") int orderID);

    @Query("select od from OrderDetail od where od.Product.ProductID = :productID")
    List<OrderDetail> findByProductId(@Param("productID") int productID);

    @Query("SELECT SUM(od.Quantity) FROM OrderDetail od")
    Long findTotalProductsSold();

}
