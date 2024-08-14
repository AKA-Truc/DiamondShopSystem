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

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Cart getCart() {
        return Cart;
    }

    public void setCart(Cart cart) {
        Cart = cart;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Promotion getPromotion() {
        return Promotion;
    }

    public void setPromotion(Promotion promotion) {
        Promotion = promotion;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public List<OrderDetail> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        OrderDetails = orderDetails;
    }
}