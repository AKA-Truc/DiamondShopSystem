package goldiounes.com.vn.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CERTIFICATES")
public class  Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int CertificateID;

    @OneToOne
    @JoinColumn(name = "DiamondID",nullable = false)
    private Diamond Diamond;

    @Column(name = "GIACode",nullable = false,unique = true)
    private String GIACode;

    public Certificate(){
        //default constructor
    }

    public Certificate(goldiounes.com.vn.models.Diamond diamond, String GIACode) {
        this.Diamond = diamond;
        this.GIACode = GIACode;
    }

    public int getCertificateID() {
        return CertificateID;
    }

    public void setCertificateID(int certificateID) {
        CertificateID = certificateID;
    }

    public goldiounes.com.vn.models.Diamond getDiamond() {
        return Diamond;
    }

    public void setDiamond(goldiounes.com.vn.models.Diamond diamond) {
        this.Diamond = diamond;
    }

    public String getGIACode() {
        return GIACode;
    }

    public void setGIACode(String GIACode) {
        this.GIACode = GIACode;
    }

    public String setGIACode() { return GIACode; }
}
