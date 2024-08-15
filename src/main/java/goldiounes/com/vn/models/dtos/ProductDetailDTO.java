package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import goldiounes.com.vn.models.entities.Product;
import goldiounes.com.vn.models.entities.Setting;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ProductDetailDTO {
    private int productDetailId;
    private int LaborCost;
    private Integer Size;
    private int inventory;

    //@JsonManagedReference
    private SettingDTO setting;

    //@JsonBackReference
    private ProductDTO product;
}
