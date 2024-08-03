package goldiounes.com.vn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "product")
    private List<Warranty> Warranties;

    @OneToMany(mappedBy = "Product")
    private List<Receipt> Receipts;

    public Product() {
        //cstor
    }

    public Product(Category category, String productName, String imageURL, double markupRate, double laborCost, double warrantyPeriod, int inventory) {
        Category = category;
        ProductName = productName;
        ImageURL = imageURL;
        MarkupRate = markupRate;
        LaborCost = laborCost;
        WarrantyPeriod = warrantyPeriod;
        Inventory = inventory;
        this.SellingPrice =  markupRate * laborCost;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public goldiounes.com.vn.models.Category getCategory() {
        return Category;
    }

    public void setCategory(goldiounes.com.vn.models.Category category) {
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

    public List<Receipt> getReceipts() {
        return Receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        Receipts = receipts;
    }

    public List<Warranty> getWarranties() {
        return Warranties;
    }

    public void setWarranties(List<Warranty> warranties) {
        Warranties = warranties;
    }
}
