package goldiounes.com.vn.models.entities;

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

    public Certificate(goldiounes.com.vn.models.entities.Diamond diamond, String giacode) {
        this.Diamond = diamond;
        this.GIACode = giacode;
    }
}
