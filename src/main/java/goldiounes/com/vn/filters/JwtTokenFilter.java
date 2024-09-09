package goldiounes.com.vn.filters;

import goldiounes.com.vn.components.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    // Đoạn mã trong JwtTokenFilter, method doFilterInternal
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                logger.info("Bypassing token for request path: {}", request.getServletPath());
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            logger.debug("Authorization Header: {}", authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.debug("No Authorization header or invalid format. Proceeding without authentication.");
                filterChain.doFilter(request, response);
                return;
            }

            final String token = authHeader.substring(7);
            final String email = jwtTokenUtil.extractEmail(token);
            logger.debug("Extracted Email from Token: {}", email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                logger.debug("Loaded UserDetails: {}", userDetails.getUsername());

                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.debug("Authentication successful. User authenticated: {}", userDetails.getUsername());
                } else {
                    logger.debug("Token validation failed for user: {}", email);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
        }
    }


    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/user-management/generate-secret-key", "GET"),
                Pair.of("/user-management/test", "GET"),
                Pair.of("/user-management/register", "POST"),
                Pair.of("/user-management/login", "POST"),
                Pair.of("/user-management/GoogleLogin", "POST"),
                Pair.of("/product-management/products", "GET"),
                Pair.of("/product-management/products/category/{keyword}/{minPrice}/{maxPrice}", "GET"),
                Pair.of("/product-management/products/{id}", "GET"),
                Pair.of("/product-management/products/{id}/productdetails", "GET"),
                Pair.of("/product-management/productdetails/min/{id}", "GET"),
                Pair.of("/forgot-password/send-otp", "POST"),
                Pair.of("/forgot-password/verify-otp", "POST"),
                Pair.of("/forgot-password/change_password/{email}", "POST")
        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        boolean isBypass = bypassTokens.stream()
                .anyMatch(token -> requestPath.startsWith(token.getFirst()) &&
                        requestMethod.equalsIgnoreCase(token.getSecond()));

        logger.debug("Bypass check for path: {}, method: {} - Result: {}", requestPath, requestMethod, isBypass);
        return isBypass;
    }
}
