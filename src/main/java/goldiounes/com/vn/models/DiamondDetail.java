package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DIAMONDDETAILS")
public class DiamondDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DiamondDetailID")
    private int DiamondDetailID;

    @ManyToOne
    @JoinColumn(name = "DiamondID", nullable = false)
    private Diamond Diamond;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    @ManyToOne
    @JoinColumn(name = "ProductDetailID", nullable = false)
    private ProductDetail ProductDetail;


    public DiamondDetail() {
        //cstor
    }

    public DiamondDetail(Diamond diamond, int quantity) {
        Diamond = diamond;
        Quantity = quantity;
    }

    public int getDiamondDetailID() {
        return DiamondDetailID;
    }

    public void setDiamondDetailID(int diamondDetailID) {
        DiamondDetailID = diamondDetailID;
    }

    public goldiounes.com.vn.models.Diamond getDiamond() {
        return Diamond;
    }

    public void setDiamond(goldiounes.com.vn.models.Diamond diamond) {
        Diamond = diamond;
    }

    public goldiounes.com.vn.models.ProductDetail getProductDetail() {
        return ProductDetail;
    }

    public void setProductDetail(goldiounes.com.vn.models.ProductDetail productDetail) {
        ProductDetail = productDetail;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}