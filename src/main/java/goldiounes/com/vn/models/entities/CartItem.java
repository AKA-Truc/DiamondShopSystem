package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CARTITEMS")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "CartItemID")
    private int CartItemID;

    @ManyToOne
    @JoinColumn(name = "CartID", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product Product;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    public CartItem() {
        //cstor
    }

    public CartItem(Cart cartID, Product product, int quantity) {
        Product = product;
        Quantity = quantity;
    }
}