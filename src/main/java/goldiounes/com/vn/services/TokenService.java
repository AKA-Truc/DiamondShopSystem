//package goldiounes.com.vn.services;
//
//import goldiounes.com.vn.components.JwtTokenUtils;
//import goldiounes.com.vn.models.entities.Token;
//import goldiounes.com.vn.models.entities.User;
//import goldiounes.com.vn.repositories.TokenRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class TokenService {
//    private static final int MAX_TOKENS = 3;
//    @Value("${jwt.expiration}")
//    private int expiration; //save to an environment variable
//
//    @Value("${jwt.expiration-refresh-token}")
//    private int expirationRefreshToken;
//
//    private final TokenRepository tokenRepository;
//    private final JwtTokenUtils jwtTokenUtil;
//
//
//    @Transactional
//    public Token addToken(User user, String token, boolean isMobileDevice) {
//        List<Token> userTokens = tokenRepository.findByUser(user);
//        int tokenCount = userTokens.size();
//        if (tokenCount >= MAX_TOKENS) {
//            boolean hasNonMobileToken = !userTokens.stream().allMatch(Token::isMobile);
//            Token tokenToDelete;
//            if (hasNonMobileToken) {
//                tokenToDelete = userTokens.stream()
//                        .filter(userToken -> !userToken.isMobile())
//                        .findFirst()
//                        .orElse(userTokens.get(0));
//            } else {
//                tokenToDelete = userTokens.get(0);
//            }
//            tokenRepository.delete(tokenToDelete);
//        }
//        long expirationInSeconds = expiration;
//        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expirationInSeconds);
//        Token newToken = Token.builder()
//                .user(user)
//                .token(token)
//                .revoked(false)
//                .expired(false)
//                .tokenType("Bearer")
//                .expirationDate(expirationDateTime)
//                .isMobile(isMobileDevice)
//                .build();
//        newToken.setRefreshToken(UUID.randomUUID().toString());
//        newToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
//        tokenRepository.save(newToken);
//        return newToken;
//
//    }
//
//    public Token refreshToken(String refreshToken, User user) throws Exception {
//        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
//        if (existingToken == null) {
//            throw new Exception("Token not found");
//        }
//        if(existingToken.getRefreshExpirationDate().compareTo(LocalDateTime.now()) < 0){
//            tokenRepository.delete(existingToken);
//            throw new Exception("Refresh token is expired");
//        }
//        String token = jwtTokenUtil.generateToken(user);
//        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
//        existingToken.setExpirationDate(expirationDateTime);
//        existingToken.setToken(token);
//        existingToken.setRefreshToken(UUID.randomUUID().toString());
//        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
//        return existingToken;
//    }
//
//    public void deleteToken(String token) {
//        Token tokenEntity = tokenRepository.findByToken(token);
//        if (tokenEntity != null) {
//            tokenRepository.delete(tokenEntity);
//        } else {
//            throw new RuntimeException("Token not found.");
//        }
//    }
//}
