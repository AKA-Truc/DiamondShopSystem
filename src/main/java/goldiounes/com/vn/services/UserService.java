package goldiounes.com.vn.services;

import goldiounes.com.vn.components.JwtTokenUtils;
import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        userRepo.deleteById(existingUser.getUserID());
        return true;
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found"));
        existingUser.setEmail(user.getEmail());
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        existingUser.setAddress(user.getAddress());
        userRepo.save(existingUser);
        return modelMapper.map(existingUser,UserDTO.class);
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

}