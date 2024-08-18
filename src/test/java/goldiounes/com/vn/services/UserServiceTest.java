package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CartDTO;
import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public class UserServiceTest {
    private UserRepo userRepo;
    private CartService cartService;
    private ModelMapper modelMapper;
    private UserService userService;
    private PointService pointService;
    private User user;
    private UserDTO userDTO;
    private CartDTO cartDTO;
    private PointDTO pointDTO;

    @BeforeEach
    void setUp() {
        userRepo = Mockito.mock(UserRepo.class);
        cartService = Mockito.mock(CartService.class);
        pointService = Mockito.mock(PointService.class);
        modelMapper = new ModelMapper();

        userService = new UserService(userRepo, cartService, pointService, modelMapper);

        user = new User();

    }
}
