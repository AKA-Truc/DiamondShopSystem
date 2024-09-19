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

    @Column(name = "MarkupRate",nullable = false)
    private double MarkupRate;

    @Column(name = "SellingPrice",nullable = false)
    private double sellingPrice;

    @Column(name = "size", nullable = true)
    private Integer Size;

    @Column(name = "Inventory", nullable = false)
    private int Inventory;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "ProductDetail")
    private List<DiamondDetail> DiamondDetails;


    public ProductDetail() {
        //cstor
    }

    public ProductDetail(Product product, Setting setting, int laborCost, double markupRate, Integer size, int inventory) {
        this.product = product;
        this.setting = setting;
        LaborCost = laborCost;
        MarkupRate = markupRate;
        Size = size;
        Inventory = inventory;
        status = "active";
    }

}
