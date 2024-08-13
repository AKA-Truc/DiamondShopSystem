package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "OrderID")
    private int OrderID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User User;

    @ManyToOne
    @JoinColumn(name = "CartID", nullable = false)
    private Cart Cart;

    @Column(name = "TotalPrice", nullable = false)
    private int TotalPrice;

    @Column(name = "Status", nullable = false)
    private String Status;

    @ManyToOne
    @JoinColumn(name = "PromotionID", nullable = false)
    private Promotion Promotion;

    @Column(name = "ShippingAddress", nullable = false)
    private String ShippingAddress;

    @OneToMany(mappedBy = "Order")
    private List<OrderDetail> OrderDetails;

    public Order() {
        //cstor
    }

    public Order(User user, Cart cart, Promotion promotion, String shippingAddress) {
        User = user;
        Cart = cart;
        Promotion = promotion;
        ShippingAddress = shippingAddress;
    }
}