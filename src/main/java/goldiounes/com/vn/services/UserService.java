package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Order;
import goldiounes.com.vn.models.User;
import goldiounes.com.vn.repositories.OrderRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(int id) {
        return userRepo.findById(id).get();
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public void deleteById(int id) {
        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isPresent()) {
            List<Order> orders = orderRepo.findByUserId(id);
            for (Order order : orders) {
                order.setUser(null);
                orderRepo.save(order);
            }
            userRepo.delete(existingUser.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}