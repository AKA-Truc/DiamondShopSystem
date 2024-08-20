package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //auto_increment
    @Column(name = "ProductID")
    private int ProductID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category Category;

    @Column(name = "ProductName", nullable = false, unique = false)
    private String ProductName;

    @Column(name = "ImageURL")
    private String ImageURL;

    @Column(name = "SubImageURL")
    private String SubImageURL;

    @Column(name = "WarrantyPeriod",nullable = false)
    private double WarrantyPeriod;

    @OneToMany(mappedBy = "product")
    private List<ProductDetail> ProductDetails;

    @OneToMany(mappedBy = "Product")
    private List<CartItem> CartItems;

    @OneToMany(mappedBy = "Product")
    private List<OrderDetail> OrderDetails;


    public Product() {
        //cstor
    }

    public Product(Category category, String productName, String imageURL, String subImageURL, double warrantyPeriod) {
        Category = category;
        ProductName = productName;
        ImageURL = imageURL;
        SubImageURL = subImageURL;
        WarrantyPeriod = warrantyPeriod;
    }
}