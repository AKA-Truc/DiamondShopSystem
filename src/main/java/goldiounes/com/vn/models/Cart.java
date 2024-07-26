package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "CARTS")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "CartID")
    private int CartID;

    @OneToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User User;

    @Column(name = "Status", nullable = false)
    private String Status;

    @OneToMany(mappedBy = "Cart")
    private List<Order> Orders;

    @OneToMany(mappedBy = "Cart")
    private List<CartItem> CartItems;

    public Cart() {
        //Cstor
    }

    public Cart(User user, String status) {
        this.User = user;
        this.Status = status;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
