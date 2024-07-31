package goldiounes.com.vn.services;

import goldiounes.com.vn.models.CartItem;
import goldiounes.com.vn.repositories.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepo cartItemRepo;

    public List<CartItem> findAll() {
        return cartItemRepo.findAll();
    }
    public CartItem findById(int id) {
        return cartItemRepo.findById(id).get();
    }
    public CartItem save(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }
    public void deleteById(int id) {
        cartItemRepo.deleteById(id);
    }
}
