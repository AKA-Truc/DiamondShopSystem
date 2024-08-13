package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "CARTS")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CartID")
    private int CartID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User User;

    @OneToMany(mappedBy = "Cart")
    private List<Order> Orders;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> CartItems;

    public Cart() {
    }

    public Cart(User user) {
        this.User = user;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public goldiounes.com.vn.models.entities.User getUser() {
        return User;
    }

    public void setUser(goldiounes.com.vn.models.entities.User user) {
        User = user;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public List<CartItem> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        CartItems = cartItems;
    }
}
