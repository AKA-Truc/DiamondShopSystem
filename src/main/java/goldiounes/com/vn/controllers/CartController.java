//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.models.entity.Cart;
//import goldiounes.com.vn.models.entity.CartItem;
//import goldiounes.com.vn.models.entity.User;
//import goldiounes.com.vn.services.CartService;
//import goldiounes.com.vn.services.CartItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/cart-management")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    @PostMapping("/carts")
//    public Cart getCartByUserId(@RequestBody User user) {
//        Cart existingCart = cartService.findCartByUserId(user.getUserID());
//        if (existingCart == null) {
//            throw new RuntimeException("Can't find cart with id " + user.getUserID());
//        }
//        return existingCart;
//    }
//
//    @GetMapping("/cart-items")
//    public List<CartItem> getAllCartItems() {
//        return cartItemService.findAll();
//    }
//
//    @PostMapping("/cart-items")
//    public CartItem addItem(@RequestBody CartItem cartItem) {
//        List<CartItem> cartItems = cartItemService.findByCarId(cartItem.getCart().getCartID());
//        if (cartItems == null) {
//            throw new RuntimeException("Can't find cart with id " + cartItem.getCart().getCartID());
//        }
//        for (CartItem cartItemIndex : cartItems) {
//            if (cartItemIndex.getCart().getCartID() == cartItem.getCart().getCartID()) {
//                cartItemIndex.setQuantity(cartItemIndex.getQuantity() + cartItem.getQuantity());
//            }
//        }
//        return cartItemService.save(cartItem);
//    }
//
//    @DeleteMapping("/cart-items/{id}")
//    public void removeCartItem(@PathVariable int id) {
//        CartItem cartItem = cartItemService.findById(id);
//        if (cartItem == null) {
//            throw new RuntimeException("Can't find cart with id " + id);
//        }
//        cartItemService.deleteById(id);
//    }
//
//    @DeleteMapping("/cart")
//    public void removeAllCartItems(@RequestBody Cart cart) {
//        Cart existingCart = cartService.findCartByUserId(cart.getUser().getUserID());
//        if (existingCart == null) {
//            throw new RuntimeException("Can't find cart with id " + cart.getUser().getUserID());
//        }
//        cartItemService.deleteByCarId(cart.getCartID());
//    }
//}
