package goldiounes.com.vn.models;

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

    @OneToOne
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
}
