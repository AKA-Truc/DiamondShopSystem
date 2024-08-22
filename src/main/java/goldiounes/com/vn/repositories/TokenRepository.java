package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entities.Token;
import goldiounes.com.vn.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String refreshToken);
}
