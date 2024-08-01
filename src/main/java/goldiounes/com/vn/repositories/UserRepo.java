package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.Email=:email")
    User findByEmail(@Param("email") String email);
}
