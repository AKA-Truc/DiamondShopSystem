package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.CartDTO;
import goldiounes.com.vn.models.dtos.CartItemDTO;
import goldiounes.com.vn.services.CartService;
import goldiounes.com.vn.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart-management")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/carts/{id}")
    public ResponseEntity<Map<String, Object>> getCarts(@PathVariable int id) {
        CartDTO cart = cartService.getCart(id);
        if (cart != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cart retrieved successfully");
            response.put("cart", cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cart not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Map<String, Object>> removeAllCartItems(@PathVariable int id) {
        boolean removed = cartItemService.removeAllCartItems(id);
        Map<String, Object> response = new HashMap<>();
        if (removed) {
            response.put("message", "All items removed from cart successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response.put("message", "Failed to remove items or cart not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart-items/{id}")
    public ResponseEntity<Map<String, Object>> getAllCartItems(@PathVariable int id) {
        List<CartItemDTO> items = cartItemService.getAllCartItems(id);
        Map<String, Object> response = new HashMap<>();
        if (items != null && !items.isEmpty()) {
            response.put("message", "Cart items retrieved successfully");
            response.put("items", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "No items found in cart");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cart-items")
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO addedItem = cartItemService.addItem(cartItemDTO);
        Map<String, Object> response = new HashMap<>();
        if (addedItem != null) {
            response.put("message", "Item added to cart successfully");
            response.put("item", addedItem);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "Failed to add item to cart");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Map<String, Object>> removeCartItem(@PathVariable int id) {
        boolean removed = cartItemService.removeCartItem(id);
        Map<String, Object> response = new HashMap<>();
        if (removed) {
            response.put("message", "Item removed from cart successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response.put("message", "Item not found or failed to remove");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cart-items/{id}")
    public ResponseEntity<Map<String, Object>> updateQuantity(@PathVariable int id, @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO updatedItem = cartItemService.updateQuantity(id, cartItemDTO);
        Map<String, Object> response = new HashMap<>();
        if (updatedItem != null) {
            response.put("message", "Item quantity updated successfully");
            response.put("item", updatedItem);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Failed to update item quantity");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
