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

}
