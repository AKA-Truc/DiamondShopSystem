package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderDetailDTO {
    private int orderDetailId;
    private int quantity;
    private Integer Size;

    //@JsonBackReference
    @JsonIgnore
    private OrderDTO order;

    //@JsonManagedReference
    private ProductDTO product;
}