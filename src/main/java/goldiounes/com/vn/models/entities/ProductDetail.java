package goldiounes.com.vn.models.entities;

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

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "SettingID", nullable = false)
    private Setting setting;

    @Column(name = "LaborCost", nullable = false)
    private int LaborCost;

    @OneToMany(mappedBy = "ProductDetail")
    private List<DiamondDetail> DiamondDetails;


    public ProductDetail() {
        //cstor
    }

    public ProductDetail(Product ProductID, Setting setting, int LaborCost) {
        this.product = ProductID;
        this.setting = setting;
        this.LaborCost = LaborCost;
    }

    public int getProductDetailID() {
        return ProductDetailID;
    }

    public void setProductDetailID(int productDetailID) {
        ProductDetailID = productDetailID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public int getLaborCost() {
        return LaborCost;
    }

    public void setLaborCost(int laborCost) {
        LaborCost = laborCost;
    }

    public List<DiamondDetail> getDiamondDetails() {
        return DiamondDetails;
    }

    public void setDiamondDetails(List<DiamondDetail> diamondDetails) {
        DiamondDetails = diamondDetails;
    }
}
