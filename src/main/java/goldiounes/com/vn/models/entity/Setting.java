package goldiounes.com.vn.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "SETTINGS")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "SettingID")
    private int SettingID;

    @Column(name = "Material", nullable = false)
    private String Material;

    @Column(name = "Price", nullable = false)
    private int Price;

    @OneToMany(mappedBy = "setting")
    private List<ProductDetail> ProductDetails;

    public Setting() {
        //cstor
    }

    public Setting(String Material, int Price) {
        this.Material = Material;
        this.Price = Price;
    }

    public int getSettingID() {
        return SettingID;
    }

    public void setSettingID(int settingID) {
        SettingID = settingID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public List<ProductDetail> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        ProductDetails = productDetails;
    }
}
