package goldiounes.com.vn.config;

import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.CartItem;
import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user.getUserID(), user.getUserName(), user.getAuthorities());
    }

    public int CartID(int userID){
        Cart cart = cartRepo.findCartByUserId(userID);
        if (cart == null) {
            throw new UsernameNotFoundException("Cart not found");
        }
        return cart.getCartID();
    }

    public boolean CheckCartItem(int userID, int cartItemID){
        Cart cart = cartRepo.findCartByUserId(userID);
        if (cart == null) {
            return false;
        }
        for(CartItem cartItem : cart.getCartItems()){
            if(cartItemID == cartItem.getCartItemID()){
                return true;
            }
        }
        return false;
    }
}
