package goldiounes.com.vn.components;

import goldiounes.com.vn.models.entities.Token;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import java.util.stream.Collectors;


import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final int KEY_SIZE = 256; // Changed to 256 bits for HS256

    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final TokenRepository tokenRepository;
    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = getSignInKey();
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getUserID());

        // Thêm danh sách quyền của người dùng vào payload
        claims.put("roles", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(signingKey, SIGNATURE_ALGORITHM)
                    .compact();
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Error generating JWT token: {}", e.getMessage());
            throw new RuntimeException("Cannot create JWT token", e);
        }
    }


    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[KEY_SIZE / 8];
        random.nextBytes(keyBytes);
        return Encoders.BASE64.encode(keyBytes);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        logger.debug("Using secret key (first 5 chars): {}", secretKey.substring(0, Math.min(secretKey.length(), 5)));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = extractClaim(token, Claims::getExpiration);
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        String email = extractClaim(token, Claims::getSubject);
        logger.debug("Extracted email from token: {}", email);
        return email;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String email = extractEmail(token);
            Token existingToken = tokenRepository.findByToken(token);
            logger.debug("Validating token for email: {}", email);
            logger.debug("Token found in repository: {}", existingToken != null);
            logger.debug("Token revoked: {}", existingToken != null && existingToken.isRevoked());
            logger.debug("Token expired: {}", isTokenExpired(token));
            logger.debug("UserDetails username: {}", userDetails.getUsername());

            if (existingToken == null || existingToken.isRevoked()) {
                logger.debug("Token is null or revoked");
                return false;
            }
            boolean emailMatches = email.equals(userDetails.getUsername());
            boolean notExpired = !isTokenExpired(token);
            logger.debug("Email :{}", userDetails.getUsername());
            logger.debug("Email matches: {}", emailMatches);
            logger.debug("Not expired: {}", notExpired);

            // Thêm kiểm tra chữ ký
            boolean signatureValid = validateSignature(token);
            logger.debug("Signature valid: {}", signatureValid);

            boolean isValid = emailMatches && notExpired && signatureValid;
            logger.debug("Token validation result: {}", isValid);
            return isValid;
        } catch (JwtException e) {
            logger.error("JWT token validation failed: {}", e.getMessage(), e);
        }
        return false;
    }

    private boolean validateSignature(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            logger.error("JWT signature validation failed: {}", e.getMessage());
            return false;
        }
    }
}