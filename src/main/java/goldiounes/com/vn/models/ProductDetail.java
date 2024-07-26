package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTDETAILS")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "ProductDetailID")
    private int ProductDetailID;

    @OneToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product ProductID;

    @ManyToOne
    @JoinColumn(name = "SettingID", nullable = false)
    private Setting SettingID;

    @Column(name = "LoborCost", nullable = false)
    private int LaborCost;

    @OneToMany(mappedBy = "productDetail")
    private List<DiamondDetail> DiamondDetails;

    public ProductDetail() {
        //cstor
    }

    public ProductDetail(Product ProductID, Setting SettingID, int LaborCost) {
        this.ProductID = ProductID;
        this.SettingID = SettingID;
        this.LaborCost = LaborCost;
    }

    public int getProductDetailID() {
        return ProductDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        ProductDetailID = productDetailID;
    }

    public Product getProductID() {
        return ProductID;
    }

    public void setProductID(Product productID) {
        ProductID = productID;
    }

    public Setting getSettingID() {
        return SettingID;
    }

    public void setSettingID(Setting settingID) {
        SettingID = settingID;
    }

    public int getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(int laborCost) {
        LaborCost = laborCost;
    }
}
