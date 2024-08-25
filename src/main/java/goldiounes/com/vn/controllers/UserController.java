package goldiounes.com.vn.controllers;

import goldiounes.com.vn.components.JwtTokenUtils;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-management")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @GetMapping("/generate-secret-key")
    public ResponseEntity<String> generateSecretKey(){
        return ResponseEntity.ok(jwtTokenUtils.generateSecretKey());
    }

    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STAFF','ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<ResponseWrapper<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        ResponseWrapper<UserDTO> response = new ResponseWrapper<>("User created successfully", createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<UserDTO>> getUser(@PathVariable int id) {
        UserDTO user = userService.getUser(id);
        if (user != null) {
            ResponseWrapper<UserDTO> response = new ResponseWrapper<>("User retrieved successfully", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<UserDTO> response = new ResponseWrapper<>("User not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>("Users retrieved successfully", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<UserDTO>> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser != null) {
            ResponseWrapper<UserDTO> response = new ResponseWrapper<>("User updated successfully", updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<UserDTO> response = new ResponseWrapper<>("User not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        ResponseWrapper<Void> response;

        if (isDeleted) {
            response = new ResponseWrapper<>("User deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("User not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/users/role/{role}")
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> getUserByRole(@PathVariable String role) {
        List<UserDTO> userDTO = userService.getUserByRole(role);
        if (userDTO != null) {
            ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>("User found", userDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<UserDTO>> response = new ResponseWrapper<>("User not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Server is working");
    }
}
