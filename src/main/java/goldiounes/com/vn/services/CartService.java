package goldiounes.com.vn.services;


import goldiounes.com.vn.models.Cart;
import goldiounes.com.vn.repositories.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;


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
}
