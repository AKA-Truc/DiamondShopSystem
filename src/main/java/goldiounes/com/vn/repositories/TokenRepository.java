package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.Token;
import goldiounes.com.vn.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t WHERE t.user.UserID=:id")
    List<Token> findByUser(int id);

    Token findByToken(String token);

    Token findByRefreshToken(String refreshToken);
}
