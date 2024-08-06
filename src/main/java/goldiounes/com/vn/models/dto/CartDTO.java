package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class CartDTO {
    private int CartID;

    @JsonIgnoreProperties("products")
    private UserDTO users;

    @JsonIgnoreProperties("carts")
    private List<OrderDTO> Orders;

    @JsonIgnoreProperties("carts")
    private List<CartItemDTO> CartItems;

    public CartDTO() {

    }

    public CartDTO(int cartID, UserDTO users) {
        CartID = cartID;
        this.users = users;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public UserDTO getUsers() {
        return users;
    }

    public void setUsers(UserDTO users) {
        this.users = users;
    }

    public List<OrderDTO> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        Orders = orders;
    }

    public List<CartItemDTO> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        CartItems = cartItems;
    }
}