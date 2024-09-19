package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "SETTINGS")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "SettingID")
    private int SettingID;

    @Column(name = "Material", nullable = false)
    private String Material;

    @Column(name = "Price", nullable = false)
    private int Price;

    @OneToMany(mappedBy = "setting")
    private List<ProductDetail> ProductDetails;

    public Setting() {
        //constructor
    }

    public Setting(String Material, int Price) {
        this.Material = Material;
        this.Price = Price;
    }
}
