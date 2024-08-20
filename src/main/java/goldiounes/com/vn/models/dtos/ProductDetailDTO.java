package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDTO {
    private int productDetailId;
    private int LaborCost;
    private double markupRate;
    private double sellingPrice;
    private Integer Size;
    private int inventory;

    //@JsonManagedReference
    private SettingDTO setting;

    //@JsonBackReference
    private ProductDTO product;

    @JsonManagedReference(value = "diamond_Product_Detail")
    private List<DiamondDetailDTO> diamondDetails;
}
