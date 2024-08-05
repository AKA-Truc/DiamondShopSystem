package goldiounes.com.vn.models.entity;

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

    public int getCartItemID() {
        return CartItemID;
    }

    public void setCartItemID(int cartItemID) {
        CartItemID = cartItemID;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public goldiounes.com.vn.models.entity.Product getProduct() {
        return Product;
    }

    public void setProduct(goldiounes.com.vn.models.entity.Product product) {
        Product = product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}