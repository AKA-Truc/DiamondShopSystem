package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

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
    private Product Product;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    public Receipt() {
        //cstor
    }

    public Receipt(Product ProductID, int Quantity) {
        this.Product = ProductID;
        this.Quantity = Quantity;
    }

    public int getReceiptID() {
        return ReceiptID;
    }

    public void setReceiptID(int receiptID) {
        ReceiptID = receiptID;
    }

    public goldiounes.com.vn.models.Product getProduct() {
        return Product;
    }

    public void setProduct(goldiounes.com.vn.models.Product product) {
        Product = product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
