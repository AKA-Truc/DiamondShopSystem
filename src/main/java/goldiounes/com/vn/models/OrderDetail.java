package goldiounes.com.vn.models;

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

    @Column(name = "Price", nullable = false)
    private double Price;

    public OrderDetail() {
        //cstor
    }

    public OrderDetail(goldiounes.com.vn.models.Order order, goldiounes.com.vn.models.Product product, int quantity, double price) {
        Order = order;
        Product = product;
        Quantity = quantity;
        Price = price;
    }

    public int getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        OrderDetailID = orderDetailID;
    }

    public Order getOrder() {
        return Order;
    }

    public void setOrder(Order order) {
        Order = order;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
