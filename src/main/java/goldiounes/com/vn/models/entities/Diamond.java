package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "DIAMONDS")
public class Diamond {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DiamondID")
    private int DiamondID;

    @Column(name = "Measurement", nullable = false)
    private Double Measurement;

    @Column(name = "Carat", nullable = false)
    private Double Carat;

    @Column(name = "Color", nullable = false)
    private String Color;

    @Column(name = "Clarity", nullable = false)
    private String Clarity;

    @Column(name = "Cut", nullable = false)
    private String Cut;

    @Column(name = "Status", nullable = false)
    private String Status;

    @Column(name = "Shape", nullable = false)
    private String Shape;

    @Column(name = "Price", nullable = false)
    private int Price;

    @Column(name = "GIACode",nullable = false,unique = true)
    private String GIACode;

    @OneToMany(mappedBy = "Diamond")
    private List<DiamondDetail> diamondDetails;

    public Diamond() {
        //constructor
    }

    public Diamond(Double measurement ,Double carat, String color, String clarity, String cut, String origin, int price, String giaCode) {
        Measurement = measurement;
        Carat = carat;
        Color = color;
        Clarity = clarity;
        Cut = cut;
        Shape = origin;
        Price = price;
        GIACode = giaCode;
        Status = "active";
    }

}
