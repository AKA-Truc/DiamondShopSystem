//package goldiounes.com.vn.filters;
//
//import goldiounes.com.vn.components.JwtTokenUtils;
//import goldiounes.com.vn.models.entities.User;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.util.Pair;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//
//public class JwtTokenFilter extends OncePerRequestFilter{
//    @Value("${api.prefix}")
//    private String apiPrefix;
//    private final UserDetailsService userDetailsService;
//    private final JwtTokenUtils jwtTokenUtil;
//    @Override
//    protected void doFilterInternal(@NonNull  HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            if(isBypassToken(request)) {
//                filterChain.doFilter(request, response); //enable bypass
//                return;
//            }
//            final String authHeader = request.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                response.sendError(
//                        HttpServletResponse.SC_UNAUTHORIZED,
//                        "authHeader null or not started with Bearer");
//                return;
//            }
//            final String token = authHeader.substring(7);
//            final String email = jwtTokenUtil.extractEmail(token);
//            if (email != null
//                    && SecurityContextHolder.getContext().getAuthentication() == null) {
//                User userDetails = (User) userDetailsService.loadUserByUsername(email);
//                if(jwtTokenUtil.validateToken(token, (UserDetails) userDetails)) {
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(
//                                    userDetails,
//                                    null,
//                                    ((UserDetails) userDetails).getAuthorities()
//                            );
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
//            filterChain.doFilter(request, response); //enable bypass
//        }catch (Exception e) {
//            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(e.getMessage());
//        }
//
//    }
//    private boolean isBypassToken(@NonNull HttpServletRequest request) {
//        final List<Pair<String, String>> bypassTokens = Arrays.asList(
//                Pair.of(String.format("%s/users/generate-secret-key", apiPrefix), "GET"),
//                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
//                Pair.of(String.format("%s/users/refresh-token", apiPrefix), "POST"),
//                Pair.of(String.format("%s/users/update_password/**", apiPrefix), "PUT"),
//                Pair.of(String.format("%s/forgot_password/verify_mail/**", apiPrefix), "POST"),
//                Pair.of(String.format("%s/forgot_password/verify_otp/**", apiPrefix), "POST"),
//                Pair.of(String.format("%s/forgot_password/change_password/**", apiPrefix), "POST")
//        );
//        String requestPath = request.getServletPath();
//        String requestMethod = request.getMethod();
//        for (Pair<String, String> token : bypassTokens) {
//            String path = token.getFirst();
//            String method = token.getSecond();
//            if (requestPath.matches(path.replace("**", ".*"))
//                    && requestMethod.equalsIgnoreCase(method)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
