package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entity.Cart;
import goldiounes.com.vn.models.entity.User;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    public Cart findById(int id) {
        return cartRepo.findById(id).get();
    }

    public Cart save(Cart cart) {
        return cartRepo.save(cart);
    }

    public void deleteById(int id) {
        cartRepo.deleteById(id);
    }

    public Cart findCartByUserId(int userId) {
        User user = userRepo.findById(userId).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return cartRepo.findCartByUserId(userId);
    }
}
