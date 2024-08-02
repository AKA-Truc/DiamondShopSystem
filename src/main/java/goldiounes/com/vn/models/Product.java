package goldiounes.com.vn.models;

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

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category Category;

    @Column(name = "ProductName", nullable = false, unique = false)
    private String ProductName;

    @Column(name = "ImageURL")
    private String ImageURL;

    @Column(name = "MarkupRate",nullable = false)
    private double MarkupRate;

    @Column(name = "LaborCost",nullable = false)
    private double LaborCost;

    @Column(name = "SellingPrice",nullable = false)
    private double SellingPrice;

    @Column(name = "WarrantyPeriod",nullable = false)
    private double WarrantyPeriod;

    @Column(name = "Inventory", nullable = false)
    private int Inventory;

    @OneToMany(mappedBy = "product")
    private List<ProductDetail> ProductDetails;

    @OneToMany(mappedBy = "Product")
    private List<CartItem> CartItems;

    @OneToMany(mappedBy = "Product")
    private List<OrderDetail> OrderDetails;

    @OneToMany(mappedBy = "Product")
    private List<Receipt> Receipt;

    public Product() {
        //cstor
    }

    public Product(Category category, String productName, String imageURL, double markupRate, double laborCost, double sellingPrice, double warrantyPeriod, int inventory) {
        Category = category;
        ProductName = productName;
        ImageURL = imageURL;
        MarkupRate = markupRate;
        LaborCost = laborCost;
        SellingPrice = sellingPrice;
        WarrantyPeriod = warrantyPeriod;
        Inventory = inventory;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        Category = category;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public double getMarkupRate() {
        return MarkupRate;
    }

    public void setMarkupRate(double markupRate) {
        MarkupRate = markupRate;
    }

    public double getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(double laborCost) {
        LaborCost = laborCost;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public double getWarrantyPeriod() {
        return WarrantyPeriod;
    }

    public void setWarrantyPeriod(double warrantyPeriod) {
        WarrantyPeriod = warrantyPeriod;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }

    public List<ProductDetail> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        ProductDetails = productDetails;
    }

    public List<CartItem> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        CartItems = cartItems;
    }

    public List<OrderDetail> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        OrderDetails = orderDetails;
    }
}