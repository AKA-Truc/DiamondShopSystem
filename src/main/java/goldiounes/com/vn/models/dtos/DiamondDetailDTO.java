package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DiamondDetailDTO {
    private int diamondDetailId;
    private String typeDiamond;
    private int quantity;
    
    private DiamondDTO diamond;

    @JsonBackReference(value = "diamond_Product_Detail")
    private ProductDetailDTO productDetail;

}
