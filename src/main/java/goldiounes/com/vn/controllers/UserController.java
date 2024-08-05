//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.models.entity.User;
//import goldiounes.com.vn.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/user-management")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        if (userService.findByEmail(user.getEmail()) != null) {
//            throw new RuntimeException("User already exists");
//        }
//        return userService.save(user);
//    }
//
//    @GetMapping("/users/{id}")
//    public User getUser(@PathVariable int id) {
//        User existingUser =  userService.findById(id);
//        if(existingUser == null) {
//            throw new RuntimeException("User not found");
//        }
//        return existingUser;
//    }
//
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        List<User> users = userService.findAll();
//        if (users.isEmpty()) {
//            throw new RuntimeException("No users found");
//        }
//        return users;
//    }
//
//    @PutMapping("/users/{id}")
//    public User updateUser(@PathVariable int id, @RequestBody User user) {
//        User existingUser = userService.findById(id);
//        if (existingUser == null) {
//            throw new RuntimeException("User not found");
//        }
//        existingUser.setUserName(user.getUserName());
//        existingUser.setPassword(user.getPassword());
//        existingUser.setEmail(user.getEmail());
//        existingUser.setAddress(user.getAddress());
//        existingUser.setRole(user.getRole());
//        existingUser.setCart(user.getCart());
//        existingUser.setOrders(user.getOrders());
//        existingUser.setWarranties(user.getWarranties());
//        return userService.save(existingUser);
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable int id) {
//        userService.deleteById(id);
//    }
//}