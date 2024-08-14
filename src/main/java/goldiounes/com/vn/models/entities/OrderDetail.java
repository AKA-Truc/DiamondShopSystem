package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ORDERDETAILS")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "OrderDetailID")
    private int OrderDetailID;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order Order;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product Product;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    public OrderDetail() {
        //cstor
    }

    public OrderDetail(goldiounes.com.vn.models.entities.Order order, goldiounes.com.vn.models.entities.Product product, int quantity) {
        Order = order;
        Product = product;
        Quantity = quantity;
    }
}
