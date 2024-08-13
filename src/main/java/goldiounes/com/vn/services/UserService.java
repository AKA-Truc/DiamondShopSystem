package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.PointDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.Point;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User newUser =  userRepo.save(user);
        UserDTO indexUser = modelMapper.map(newUser,UserDTO.class);
        cartService.createCart(indexUser);
        Point newPoint = new Point();
        newPoint.setUser(user);
        newPoint.setPoints(0);
        pointService.createPoint(modelMapper.map(newPoint, PointDTO.class));
        return modelMapper.map(newUser,UserDTO.class);
    }

    public void deleteUser(int id) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found"));
        userRepo.deleteById(existingUser.getUserID());
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
}