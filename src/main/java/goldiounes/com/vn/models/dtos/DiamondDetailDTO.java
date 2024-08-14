package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DiamondDetailDTO {
    private int diamondDetailId;
    private int quantity;

    //@JsonBackReference
    @JsonIgnore
    private DiamondDTO diamond;

    //@JsonBackReference
    @JsonIgnore
    private ProductDetailDTO productDetail;

}
