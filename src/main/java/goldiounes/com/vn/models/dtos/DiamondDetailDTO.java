package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class DiamondDetailDTO {
    private int diamondDetailId;
    private int quantity;

    //@JsonBackReference
    private DiamondDTO diamond;

    //@JsonBackReference
    private ProductDetailDTO productDetail;

}
