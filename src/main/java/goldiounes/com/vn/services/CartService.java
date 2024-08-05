package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dto.CartDTO;
import goldiounes.com.vn.models.dto.UserDTO;
import goldiounes.com.vn.models.entity.Cart;
import goldiounes.com.vn.models.entity.User;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CartDTO getCartByUserId(int id) {
        User existingUser = userRepo.findById(id).get();
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        Cart existingCart = cartRepo.findCartByUserId(id);
        if (existingCart == null) {
            throw new RuntimeException("Cart not found");
        }
        return modelMapper.map(existingCart,new TypeToken<CartDTO>(){}.getType());
    }

    public CartDTO createCart(User user) {
       Optional<User> existingUser = userRepo.findById(user.getUserID());
       if (existingUser == null) {
           throw new RuntimeException("User not found");
       }
       Cart cart = new Cart(user);
       cartRepo.save(cart);
       return modelMapper.map(cart,new TypeToken<CartDTO>(){}.getType());
    }

    public void deleteCart(int id) {
        Cart existingCart = cartRepo.findById(id).get();
        if (existingCart == null) {
            throw new RuntimeException("Cart not found");
        }
        cartRepo.delete(existingCart);
    }

//    public CartDTO updateCart(int id,CartDTO cartDTO) {
//        Cart existingCart = cartRepo.findById(id).get();
//        if (existingCart == null) {
//            throw new RuntimeException("Cart not found");
//        }
//        existingCart.setUser(cartDTO.getUsers());
//        cartRepo.save(existingCart);
//        return modelMapper.map(existingCart,new TypeToken<CartDTO>(){}.getType());
//    }

    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepo.findAll();
        if (carts == null) {
            throw new RuntimeException("Cart not found");
        }
        return modelMapper.map(carts,new TypeToken<List<CartDTO>>(){}.getType());
    }
}
