package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.Email=:email")
    User findByEmail(@Param("email") String email);

    @Query("select u from User u where u.Role=:role")
    List<User> findByRole(@Param("role") String role);

    @Query("SELECT u FROM User u JOIN  Order o ON u.UserID = o.User.UserID GROUP BY u.UserID")
    List<User> findTopUserBuying();
}
