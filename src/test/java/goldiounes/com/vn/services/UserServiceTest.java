package goldiounes.com.vn.services;

import goldiounes.com.vn.components.JwtTokenUtils;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepo userRepo;
    private CartService cartService;
    private PointService pointService;
    private ModelMapper modelMapper;
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtTokenUtils jwtTokenUtils;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        userRepo = Mockito.mock(UserRepo.class);
        cartService = Mockito.mock(CartService.class);
        pointService = Mockito.mock(PointService.class);
        modelMapper = new ModelMapper();

        // Sử dụng mock cho PasswordEncoder
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        jwtTokenUtils = Mockito.mock(JwtTokenUtils.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);

        userService = new UserService(userRepo, cartService, pointService, modelMapper, passwordEncoder, jwtTokenUtils, authenticationManager);
    }



    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUserID(1);
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUserID(2);
        user2.setEmail("user2@example.com");

        List<User> userList = Arrays.asList(user1, user2);

        when(userRepo.findAll()).thenReturn(userList);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setUserID(1);
        user.setEmail("user@example.com");

        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUser(1);

        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newuser@example.com");
        userDTO.setPassword("password123");

        User user = new User();
        user.setUserID(1);
        user.setEmail("newuser@example.com");

        when(userRepo.findByEmail("newuser@example.com")).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals("newuser@example.com", result.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUserID(1);
        user.setEmail("user@example.com");

        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        boolean result = userService.deleteUser(1);

        assertTrue(result);
        verify(userRepo, times(1)).deleteById(1);
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setUserID(1);
        user.setEmail("user@example.com");

        when(userRepo.findByEmail("user@example.com")).thenReturn(user);

        UserDTO result = userService.findByEmail("user@example.com");

        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
        verify(userRepo, times(1)).findByEmail("user@example.com");
    }

    @Test
    void testLogin() throws Exception {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole("customer");

        when(userRepo.findByEmail("user@example.com")).thenReturn(user);

        // Giữ stub cho phương thức matches
        when(passwordEncoder.matches("password123", user.getPassword())).thenReturn(true);

        when(jwtTokenUtils.generateToken(user)).thenReturn("dummyToken");

        String token = userService.login("user@example.com", "password123", "ROLE_USER");

        assertNotNull(token);
        assertEquals("dummyToken", token);
        verify(jwtTokenUtils, times(1)).generateToken(user);
    }

}
