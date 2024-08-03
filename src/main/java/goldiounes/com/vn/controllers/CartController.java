package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.Cart;
import goldiounes.com.vn.models.CartItem;
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

    @PostMapping("/carts")
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.save(cart);
    }

    @GetMapping("/carts/{id}")
    public Cart getCartById(@PathVariable int id) {
        return cartService.findById(id);
    }

    @GetMapping("/carts")
    public List<Cart> getAllCarts() {
        return cartService.findAll();
    }

    @PutMapping("/carts/{id}")
    public Cart updateCart(@PathVariable int id, @RequestBody Cart cart) {
        Cart existingCart = cartService.findById(id);
        if (existingCart != null) {
            existingCart.setUser(cart.getUser());
            return cartService.save(existingCart);
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    @DeleteMapping("/carts/{id}")
    public void deleteCart(@PathVariable int id) {
        cartService.deleteById(id);
    }

    @GetMapping("/cart-items")
    public List<CartItem> getAllCartItems() {
        return cartItemService.findAll();
    }

    @PostMapping("/cart-items")
    public CartItem createCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.save(cartItem);
    }

    @PutMapping("/cart-items/{id}")
    public CartItem updateCartItem(@PathVariable int id, @RequestBody CartItem cartItem) {
        CartItem existingCartItem = cartItemService.findById(id);
        if (existingCartItem != null) {
            existingCartItem.setCart(cartItem.getCart());
            existingCartItem.setProduct(cartItem.getProduct());
            existingCartItem.setQuantity(cartItem.getQuantity());
            return cartItemService.save(existingCartItem);
        } else {
            throw new RuntimeException("CartItem not found");
        }
    }

    @DeleteMapping("/cart-items/{id}")
    public void deleteCartItem(@PathVariable int id) {
        cartItemService.deleteById(id);
    }
}
