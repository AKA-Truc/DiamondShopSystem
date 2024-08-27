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

    public CartItemDTO addItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);
        Cart existingCart = cartRepo.findById(cartItem.getCart().getCartID())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product existingProduct = productRepo.findById(cartItem.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<CartItem> cartItems = cartItemRepo.findByCartID(existingCart.getCartID());

        CartItem updatedCartItem = null;
        boolean itemExists = false;

        for (CartItem cartItem1 : cartItems) {
            if (cartItem1.getProduct().getProductID() == cartItem.getProduct().getProductID()) {
                cartItem1.setQuantity(cartItemDTO.getQuantity() + cartItem1.getQuantity());
                cartItemRepo.save(cartItem1);
                itemExists = true;
                updatedCartItem = cartItem1;
                break;
            }
        }
        if (!itemExists) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(existingCart);
            newCartItem.setProduct(existingProduct);
            newCartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemRepo.save(newCartItem);
            updatedCartItem = newCartItem;
        }
        return modelMapper.map(updatedCartItem, CartItemDTO.class);
    }


    public boolean removeCartItem(int id) {
        CartItem existingCartItem = cartItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItemRepo.delete(existingCartItem);
        return false;
    }

    @Transactional
    public boolean removeAllCartItems(int id) {
        Cart existingCart = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepo.deleteByCartID(existingCart.getCartID());
        return true;
    }

    public List<CartItemDTO> findByCarId(int id) {
        Cart existingCart = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepo.findByCartID(id);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found");
        }
        return modelMapper.map(cartItems,new TypeToken<List<CartItemDTO>>(){}.getType());
    }

    public CartItemDTO updateQuantity(int id,CartItemDTO cartItemDTO) {
        CartItem cartItem = modelMapper.map(cartItemDTO, CartItem.class);
        Cart existingCart = cartRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem existingCartItem = cartItemRepo.findById(cartItem.getCart().getCartID())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        existingCartItem.setQuantity(cartItem.getQuantity());
        cartItemRepo.save(existingCartItem);
        return modelMapper.map(existingCartItem, new TypeToken<CartItemDTO>(){}.getType());
    }


    public List<CartItemDTO> findByProductId(int id) {
        List<CartItem> cartItems = cartItemRepo.findByProductID(id);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found");
        }
        return modelMapper.map(cartItems,new TypeToken<List<CartItemDTO>>(){}.getType());
    }

    public boolean deleteByID(int id) {
        if (cartItemRepo.existsById(id)) {
            cartItemRepo.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

}
