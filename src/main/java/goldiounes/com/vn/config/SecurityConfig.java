//package goldiounes.com.vn.config;
//
//import goldiounes.com.vn.models.entities.User;
//import goldiounes.com.vn.repositories.UserRepo;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SecurityConfig {
//
//    private final UserRepo userRepo;
//    Logger logger = LogManager.getLogger(SecurityConfig.class);
//    @Autowired
//    public SecurityConfig(UserRepo userRepo) {
//        this.userRepo = userRepo;
//        logger.info("UserRepository bean initialized");
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return email -> {
//            User user = userRepo.findByEmail(email);
//            if (user == null) {
//                throw new RuntimeException("Cannot find user with email =" + email);
//            }
//            return convertToUserDetails(user);
//        };
//    }
//
//    private UserDetails convertToUserDetails(User user) {
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUserName())
//                .password(user.getPassword())
//                .roles(user.getRole())
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration config
//    ) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//}
