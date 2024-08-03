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
    private int Carat;

    @Column(name = "Color", nullable = false)
    private String Color;

    @Column(name = "Clariry", nullable = false)
    private int Clariry;

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

    public Diamond( int carat, String color, int clariry, String cut, String origin) {
        Carat = carat;
        Color = color;
        Clariry = clariry;
        Cut = cut;
        Origin = origin;
    }

    public int getDiamondID() {
        return DiamondID;
    }

    public void setDiamondID(int diamondID) {
        DiamondID = diamondID;
    }

    public int getCarat() {
        return Carat;
    }

    public void setCarat(int carat) {
        Carat = carat;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getClariry() {
        return Clariry;
    }

    public void setClariry(int clariry) {
        Clariry = clariry;
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
