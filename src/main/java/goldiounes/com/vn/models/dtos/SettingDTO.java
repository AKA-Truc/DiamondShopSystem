package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SettingDTO {
    private int settingId;
    private String material;
    private int price;

    //@JsonBackReference
    private ProductDetailDTO productDetails;
}
