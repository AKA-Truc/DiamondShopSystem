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

    @Column(name = "DiamondName", nullable = false)
    private String DiamondName;

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

    @Column(name = "Origin", nullable = false)
    private String Origin;

    @Column(name = "Price", nullable = false)
    private int Price;

    @OneToMany(mappedBy = "Diamond")
    private List<DiamondDetail> diamondDetails;

    @OneToOne(mappedBy = "Diamond")
    private Certificate certificate;

    public Diamond() {
        //constructor
    }

    public Diamond(String diamondName ,Double carat, String color, String clarity, String cut, String origin, int Price) {
        DiamondName = diamondName;
        Carat = carat;
        Color = color;
        Clarity = clarity;
        Cut = cut;
        Origin = origin;
        this.Price = Price;
    }

}
