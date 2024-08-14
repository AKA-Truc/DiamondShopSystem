package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class CartItemDTO {

    private int CartItemID;
    private int Quantity;

    @JsonBackReference
    private CartDTO cart;

    @JsonManagedReference
    private ProductDTO product;

    public CartItemDTO() {
    }

    public CartItemDTO(int cartItemID, int quantity, CartDTO cart, ProductDTO product) {
        CartItemID = cartItemID;
        Quantity = quantity;
        this.cart = cart;
        this.product = product;
    }

    public int getCartItemID() {
        return CartItemID;
    }

    public void setCartItemID(int cartItemID) {
        CartItemID = cartItemID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}