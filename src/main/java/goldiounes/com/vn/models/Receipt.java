package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "RECEIPTS")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "ReceiptID", unique = true, nullable = false)
    private int ReceiptID;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product ProductID;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    @OneToMany(mappedBy = "Receipt")
    private List<Product> Products;

    public Receipt() {
        //cstor
    }

    public Receipt(Product ProductID, int Quantity) {
        this.ProductID = ProductID;
        this.Quantity = Quantity;
    }

    public int getReceiptID() {
        return ReceiptID;
    }

    public void setReceiptID(int receiptID) {
        ReceiptID = receiptID;
    }

    public Product getProductID() {
        return ProductID;
    }

    public void setProductID(Product productID) {
        ProductID = productID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}
