package goldiounes.com.vn.services;

import goldiounes.com.vn.models.User;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> findAll() {
        return userRepo.findAll();
    }
    public User findById(int id) {
        return userRepo.findById(id).get();
    }
    public User save(User user) {
        return userRepo.save(user);
    }
    public void deleteById(int id) {
        userRepo.deleteById(id);
    }
}
