package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.UserDTO;
import goldiounes.com.vn.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Test public void testCreateUser() {
        UserDTO userDTO = new UserDTO(1, "Truc", "123456aa", "pthanhtruca3@gmail.com", "staff", "QuangNgai");

        UserDTO result = userService.createUser(userDTO);
    }
}
