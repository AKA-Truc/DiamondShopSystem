package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CartItemDTO;
import goldiounes.com.vn.models.entities.Cart;
import goldiounes.com.vn.models.entities.CartItem;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.repositories.CartItemRepo;
import goldiounes.com.vn.repositories.CartRepo;
import goldiounes.com.vn.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<CartItemDTO> getAllCartItems(int id) {
        Cart existingCart = cartRepo.findCartByUserId(id);
        if (existingCart == null) {
            throw new RuntimeException("No cart found");
        }
        List<CartItem> cartItems = cartItemRepo.findByCartID(existingCart.getCartID());
        return modelMapper.map(cartItems,new TypeToken<List<CartItemDTO>>(){}.getType());
    }

    public CartItem findById(int id) {
        return cartItemRepo.findById(id).orElse(null);
    }

    public CartItemDTO addItem(CartItemDTO cartItemDTO) {
        Cart existingCart = cartRepo.findById(cartItemDTO.getCart().getCartID())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product existingProduct = productRepo.findById(cartItemDTO.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<CartItem> cartItems = cartItemRepo.findByCartID(existingCart.getCartID());
        CartItem updatedCartItem = null;
        boolean itemExists = false;

        for (CartItem cartItem1 : cartItems) {
            if (cartItem1.getProduct().getProductID() == cartItemDTO.getProduct().getProductID()) {
                cartItem1.setQuantity(cartItemDTO.getQuantity() + cartItem1.getQuantity());
                cartItemRepo.save(cartItem1);
                itemExists = true;
                updatedCartItem = cartItem1;
                break;
            }
        }
        if (!itemExists) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(existingCart);
            cartItem.setProduct(existingProduct);
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemRepo.save(cartItem);
            updatedCartItem = cartItem;
        }
        return modelMapper.map(updatedCartItem, CartItemDTO.class);
    }


    public boolean removeCartItem(int id) {
        CartItem existingCartItem = cartItemRepo.findById(id).get();
        if (existingCartItem == null) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepo.delete(existingCartItem);
        return false;
    }

    @Transactional
    public boolean removeAllCartItems(int id) {
        Cart existingCart = cartRepo.findById(id).get();
        if (existingCart == null) {
            throw new RuntimeException("Cart not found");
        }
        cartItemRepo.deleteByCartID(id);
        return false;
    }

    public List<CartItemDTO> findByCarId(int id) {
        Cart existingCart = cartRepo.findById(id).get();
        if (existingCart == null) {
            throw new RuntimeException("Cart not found");
        }
        List<CartItem> cartItems = cartItemRepo.findByCartID(id);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found");
        }
        return modelMapper.map(cartItems,new TypeToken<List<CartItemDTO>>(){}.getType());
    }

    public CartItemDTO updateQuantity(int id,CartItemDTO cartItemDTO) {
        Cart existingCart = cartRepo.findById(id).get();
        if (existingCart == null) {
            throw new RuntimeException("Cart not found");
        }
        CartItem existingCartItem = cartItemRepo.findById(cartItemDTO.getCart().getCartID()).get();
        if (existingCartItem == null) {
            throw new RuntimeException("Cart item not found");
        }
        existingCartItem.setQuantity(cartItemDTO.getQuantity());
        cartItemRepo.save(existingCartItem);
        return modelMapper.map(existingCartItem, new TypeToken<CartItemDTO>(){}.getType());
    }
}
