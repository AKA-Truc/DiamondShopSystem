package goldiounes.com.vn.models.dtos;

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
