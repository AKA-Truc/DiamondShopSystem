package goldiounes.com.vn.controllers;

import goldiounes.com.vn.config.CustomUserDetails;
import goldiounes.com.vn.models.dtos.CartDTO;
import goldiounes.com.vn.models.dtos.CartItemDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.CartService;
import goldiounes.com.vn.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/cart-management")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    // Lấy thông tin giỏ hàng dựa vào cartId
    @GetMapping("/carts/{cartId}")
    @PreAuthorize(
            "hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or " +
                    "(hasAuthority('ROLE_CUSTOMER') and #cartId == #authentication.principal.id)"
    )
    public ResponseEntity<ResponseWrapper<CartDTO>> getCarts(@PathVariable int cartId, Authentication authentication) {
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        CartDTO cart = cartService.getCart(cartId);

        if (cart == null) {
            return new ResponseEntity<>(new ResponseWrapper<>("Cart not found", null), HttpStatus.NOT_FOUND);
        }

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"))) {
            if (cart.getUser().getUserId() != currentUser.getId()) {
                return new ResponseEntity<>(new ResponseWrapper<>("Access denied", null), HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>(new ResponseWrapper<>("Cart retrieved successfully", cart), HttpStatus.OK);
    }


    // Xóa toàn bộ các mặt hàng trong giỏ hàng dựa vào cartId
    @DeleteMapping("/carts/{cartId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or " +
            "(hasAuthority('ROLE_CUSTOMER') and " +
            "@customUserDetailsService.CartID(#authentication.principal.id) == #cartId)")
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

    // Lấy tất cả các mặt hàng trong giỏ hàng dựa vào cartId
    @GetMapping("/cart-items/{cartId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or " +
            "(hasAuthority('ROLE_CUSTOMER') and " +
            "@customUserDetailsService.CartID(#authentication.principal.id) == #cartId)")
    public ResponseEntity<ResponseWrapper<List<CartItemDTO>>> getAllCartItems(@PathVariable int cartId) {
        List<CartItemDTO> items = cartItemService.getAllCartItems(cartId);
        if (items != null && !items.isEmpty()) {
            ResponseWrapper<List<CartItemDTO>> response = new ResponseWrapper<>("Cart items found", items);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<List<CartItemDTO>> response = new ResponseWrapper<>("Cart items not found", items);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Thêm một mặt hàng vào giỏ hàng
    @PostMapping("/cart-items")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<CartItemDTO>> addItem(@RequestBody CartItemDTO cartItemDTO, Authentication authentication) {
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        CartItemDTO addedItem = cartItemService.addItem(cartItemDTO, currentUser.getId());
        if (addedItem != null) {
            ResponseWrapper<CartItemDTO> response = new ResponseWrapper<>("Item added successfully", addedItem);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            ResponseWrapper<CartItemDTO> response = new ResponseWrapper<>("Item not found", cartItemDTO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa một mặt hàng trong giỏ hàng dựa vào cartItemId
    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or " +
            "(hasAuthority('ROLE_CUSTOMER') and " +
            "@customUserDetailsService.CheckCartItem(#authentication.principal.id,#cartItemId))")
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

    // Cập nhật số lượng của một mặt hàng trong giỏ hàng
    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or " +
            "(hasAuthority('ROLE_CUSTOMER') and " +
            "@customUserDetailsService.CheckCartItem(#authentication.principal.id,#cartItemId))")
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
