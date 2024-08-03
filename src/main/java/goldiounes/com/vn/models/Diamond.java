package goldiounes.com.vn.models;

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
    private float Carat;

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
        //cstor
    }

    public Diamond( float carat, String color, String clarity, String cut, String origin) {
        Carat = carat;
        Color = color;
        Clarity = clarity;
        Cut = cut;
        Origin = origin;
    }

    public int getDiamondID() {
        return DiamondID;
    }

    public void setDiamondID(int diamondID) {
        DiamondID = diamondID;
    }

    public float getCarat() {
        return Carat;
    }

    public void setCarat(float carat) {
        Carat = carat;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getClarity() {
        return Clarity;
    }

    public void setClarity(String clarity) {
        Clarity = clarity;
    }

    public String getCut() {
        return Cut;
    }

    public void setCut(String cut) {
        Cut = cut;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public List<DiamondDetail> getDiamondDetails() {
        return diamondDetails;
    }

    public void setDiamondDetails(List<DiamondDetail> diamondDetails) {
        this.diamondDetails = diamondDetails;
    }
}
