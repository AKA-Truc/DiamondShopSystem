package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CartItemDTO {
    private int cartItemId;
    private int quantity;

    @JsonBackReference
    private CartDTO cart;

    //@JsonBackReference
    private ProductDTO product;
}