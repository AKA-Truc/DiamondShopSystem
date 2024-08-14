package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class CartDTO {
    private int CartID;

    @JsonBackReference
    private UserDTO users;

    @JsonManagedReference
    private List<OrderDTO> Orders;

    @JsonManagedReference
    private List<CartItemDTO> CartItems;

    public CartDTO() {
    }

    public CartDTO( UserDTO users) {
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