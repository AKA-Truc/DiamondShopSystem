package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    @Query("select c FROM CartItem c where c.cart.CartID=:cartid")
    List<CartItem> findByCartID(@Param("cartid") int cartid);

    @Modifying
    @Query("delete from CartItem c where c.cart.CartID = :cartid")
    void deleteByCartID(@Param("cartid") int cartid);

    @Query("SELECT ci FROM CartItem ci WHERE ci.Product.ProductID=:productID")
    List<CartItem> findByProductID(@Param("productID") int productID);

}
