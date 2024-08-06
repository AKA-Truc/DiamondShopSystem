package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.UserDTO;
import goldiounes.com.vn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id,userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}