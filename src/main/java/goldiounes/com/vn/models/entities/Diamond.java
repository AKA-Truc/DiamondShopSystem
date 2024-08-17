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

    @Column(name = "Carat", nullable = false)
    private Double Carat;

    @Column(name = "Color", nullable = false)
    private String Color;

    @Column(name = "Clarity", nullable = false)
    private String Clarity;

    @Column(name = "Cut", nullable = false)
    private String Cut;

    @Column(name = "Origin", nullable = false)
    private String Origin;

    @OneToMany(mappedBy = "Diamond")
    private List<DiamondDetail> diamondDetails;

    @OneToOne(mappedBy = "Diamond")
    private Certificate certificate;

    public Diamond() {
        //constructor
    }

    public Diamond( Double carat, String color, String clarity, String cut, String origin) {
        Carat = carat;
        Color = color;
        Clarity = clarity;
        Cut = cut;
        Origin = origin;
    }

}
