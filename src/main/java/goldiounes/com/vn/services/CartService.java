package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CartDTO;
import goldiounes.com.vn.models.dtos.UserDTO;
import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CartDTO getCart(int id) {
        Cart existingCart = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        if (existingCart == null) {
            throw new RuntimeException("Cart not found");
        }
        return modelMapper.map(existingCart,new TypeToken<CartDTO>(){}.getType());
    }

    public CartDTO createCart(UserDTO userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        User existingUser = userRepo.findById(user.getUserID())
                .orElseThrow(()-> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(existingUser);
        Cart saveCart = cartRepo.save(cart);
        return modelMapper.map(saveCart,CartDTO.class);
    }

    public void deleteCart(int id) {
        Cart existingCart = cartRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Cart not found"));
        cartRepo.delete(existingCart);
    }

    public CartDTO updateCart(int id,CartDTO cartDTO) {
        Cart cart = modelMapper.map(cartDTO,Cart.class);
        Cart existingCart = cartRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Cart not found"));
        existingCart.setUser(cart.getUser());
        cartRepo.save(existingCart);
        return modelMapper.map(existingCart,CartDTO.class);
    }

    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepo.findAll();
        if (carts.isEmpty()) {
            throw new RuntimeException("List Cart is empty");
        }
        return modelMapper.map(carts,new TypeToken<List<CartDTO>>(){}.getType());
    }
}
