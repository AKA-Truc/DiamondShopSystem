package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.CartDTO;
import goldiounes.com.vn.models.dtos.CartItemDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.CartService;
import goldiounes.com.vn.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-management")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/carts/{cartId}")
    public ResponseEntity<ResponseWrapper<CartDTO>> getCarts(@PathVariable int cartId) {
        CartDTO cart = cartService.getCart(cartId);
        if (cart != null) {
            ResponseWrapper<CartDTO> response = new ResponseWrapper<>("Cart created successfully", cart);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<CartDTO> response = new ResponseWrapper<>("Cart not found", cart);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<ResponseWrapper<Void>> removeAllCartItems(@PathVariable int cartId) {
        boolean removed = cartItemService.removeAllCartItems(cartId);
        if (removed) {
            ResponseWrapper<Void> response = new ResponseWrapper<>("All items are deleted", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Item not found or failed to remove", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cart-items/{cartId}")
    public ResponseEntity<ResponseWrapper<List<CartItemDTO>>> getAllCartItems(@PathVariable int cartId) {
        List<CartItemDTO> items = cartItemService.getAllCartItems(cartId);
        if (items != null && !items.isEmpty()) {
            ResponseWrapper<List<CartItemDTO>> response = new ResponseWrapper<>("Cart items found", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<CartItemDTO>> response =  new ResponseWrapper<>("Cart items not found", items);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cart-items")
    public ResponseEntity<ResponseWrapper<CartItemDTO>> addItem(@RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO addedItem = cartItemService.addItem(cartItemDTO);
        if (addedItem != null) {
            ResponseWrapper<CartItemDTO> response = new ResponseWrapper<>("Item added successfully", addedItem);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            ResponseWrapper<CartItemDTO> response = new ResponseWrapper<>("Item not found", cartItemDTO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public ResponseEntity<ResponseWrapper<Void>> removeCartItem(@PathVariable int cartItemId) {
        boolean removed = cartItemService.removeCartItem(cartItemId);
        if (removed) {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Item removed successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Item not found or failed to remove", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cart-items/{id}")
    public ResponseEntity<ResponseWrapper<CartItemDTO>> updateQuantity(@PathVariable int id, @RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO updatedItem = cartItemService.updateQuantity(id, cartItemDTO);
        if (updatedItem != null) {
            ResponseWrapper<CartItemDTO> response = new ResponseWrapper<>("Item updated successfully", updatedItem);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<CartItemDTO> response = new ResponseWrapper<>("Item not found or failed to update", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
