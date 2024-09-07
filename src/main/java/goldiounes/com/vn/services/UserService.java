package goldiounes.com.vn.services;

import goldiounes.com.vn.components.JwtTokenUtils;
import goldiounes.com.vn.models.dtos.ChangePasswordDTO;
import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.models.dtos.TokenDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.Point;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtDecoder jwtDecoder;


    public UserService(UserRepo userRepo, CartService cartService, PointService pointService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.cartService = cartService;
        this.pointService = pointService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return modelMapper.map(users,new TypeToken<List<UserDTO>>(){}.getType());
    }

    public UserDTO getUser(int id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found"));
        return modelMapper.map(existingUser,new TypeToken<UserDTO>(){}.getType());
    }

    public UserDTO findByEmail(String email) {
        User existingUser = userRepo.findByEmail(email);
        if (existingUser == null) {
            throw new RuntimeException("No user found");
        }
        return modelMapper.map(existingUser,new TypeToken<UserDTO>(){}.getType());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("active");
        User newUser =  userRepo.save(user);
        UserDTO indexUser = modelMapper.map(newUser,UserDTO.class);
        cartService.createCart(indexUser);
        Point newPoint = new Point();
        newPoint.setUser(user);
        newPoint.setPoints(0);
        pointService.createPoint(modelMapper.map(newPoint, PointDTO.class));
        return modelMapper.map(newUser,UserDTO.class);
    }

    public boolean deleteUser(int id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found"));
        existingUser.setStatus("inactive");
        pointService.deleteById(existingUser.getPoint().getPointID());
        cartService.deleteCart(existingUser.getCart().getCartID());
        userRepo.save(existingUser);
        return true;
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found"));
        existingUser.setEmail(user.getEmail());
        existingUser.setUserName(user.getUserName());
        existingUser.setAddress(user.getAddress());
        existingUser.setGender(user.getGender());
        userRepo.save(existingUser);
        return modelMapper.map(existingUser,UserDTO.class);
    }

    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw new RuntimeException("Token is expired");
        }
        String email = jwtTokenUtils.extractEmail(token);
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new Exception("User not found");
        }
    }

    public List<UserDTO> getUserByRole(String Role) {
        List<User> existingUser = userRepo.findByRole(Role);
        if (existingUser == null) {
            throw new RuntimeException("No user found");
        }
        return modelMapper.map(existingUser,new TypeToken<List<UserDTO>>(){}.getType());
    }

    public boolean changePassword(String username, ChangePasswordDTO changePasswordDTO) {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (!changePasswordDTO.getPassword().equals(changePasswordDTO.getRetypePassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepo.save(user);
        return true;
    }

    public String login(String email, String password, String Role) throws Exception {
        User existingUser = userRepo.findByEmail(email);
        if(existingUser == null){
            throw new RuntimeException("Invalid user account / password");
        }

        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new RuntimeException("INVALID EMAIL OR PASSWORD");

        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password,
                existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(existingUser);
    }

    public String GoogleLogin(TokenDTO tokenRequest) {
        try {
            String token = tokenRequest.getToken();
            // Decode JWT token
            Jwt jwt = jwtDecoder.decode(token);
            String email = jwt.getClaim("email");
            String name = jwt.getClaim("name");

            User existingUser = userRepo.findByEmail(email);
            if (existingUser == null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(email);
                userDTO.setUserName(name);
                userDTO.setPassword("GOOGLE_USER"); // You might want to handle this more securely
                userDTO.setRole("customer"); // Fixed role typo

                UserDTO newUser = createUser(userDTO);
                return login(newUser.getEmail(), "GOOGLE_USER", newUser.getRole());
            }
            return login(existingUser.getEmail(), "GOOGLE_USER", existingUser.getRole());
        } catch (Exception e) {
            // Ghi lại thông tin lỗi chi tiết
            System.err.println("Lỗi khi đăng nhập bằng Google: " + e.getMessage());
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình đăng nhập bằng Google", e);
        }
    }
}
