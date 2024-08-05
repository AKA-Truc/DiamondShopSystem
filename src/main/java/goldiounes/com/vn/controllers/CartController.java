package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.CartDTO;
import goldiounes.com.vn.models.dto.CartItemDTO;
import goldiounes.com.vn.models.dto.ProductDTO;
import goldiounes.com.vn.models.entity.CartItem;
import goldiounes.com.vn.services.CartService;
import goldiounes.com.vn.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-management")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/carts/{id}")
    public CartDTO getCarts(@PathVariable int id) {
        return cartService.getCartByUserId(id);
    }

    //xoa tat ca cac sp trong gio hang
    @DeleteMapping("/carts/{id}")
    public void removeAllCartItems(@PathVariable int id) {
        cartItemService.removeAllCartItems(id);
    }

    //in ra tat ca sp trong gio hang
    @GetMapping("/cart-items/{id}")
    public List<CartItemDTO> getAllCartItems(@PathVariable int id) {
        return cartItemService.getAllCartItems(id);
    }

    //Them sp vao gio hang
    @PostMapping("/cart-items")
    public CartItemDTO addItem(@RequestBody CartItemDTO cartItemDTO) {
        return cartItemService.addItem(cartItemDTO);
    }

    //xoa 1 sp trong gio hang
    @DeleteMapping("/cart-items/{id}")
    public void removeCartItem(@PathVariable int id) {
        cartItemService.removeCartItem(id);
    }

    //update số lượng
    @PutMapping("/cart-items/{id}")
    public CartItemDTO updateQuantity(@PathVariable int id, @RequestBody CartItemDTO cartItemDTO) {
        return cartItemService.updateQuantity(id,cartItemDTO);
    }
}
