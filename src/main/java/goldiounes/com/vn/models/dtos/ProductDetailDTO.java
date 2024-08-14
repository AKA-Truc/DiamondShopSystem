package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Data
public class ProductDetailDTO {
    private int productDetailId;
    private String description;
    private String specifications;

    //@JsonManagedReference
    private SettingDTO setting;

    //@JsonBackReference
    @JsonIgnore
    private ProductDTO product;
}
