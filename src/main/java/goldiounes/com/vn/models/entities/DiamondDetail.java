package goldiounes.com.vn.models.entities;

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

    @Column(name = "TypeDiamond", nullable = false)
    private String TypeDiamond;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    @ManyToOne
    @JoinColumn(name = "ProductDetailID", nullable = false)
    private ProductDetail ProductDetail;


    public DiamondDetail() {
        //cstor
    }

    public DiamondDetail(Diamond diamond, int quantity, String typeDiamond) {
        Diamond = diamond;
        Quantity = quantity;
        TypeDiamond = typeDiamond;
    }

}