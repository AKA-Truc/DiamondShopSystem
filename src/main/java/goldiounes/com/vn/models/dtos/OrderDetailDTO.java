package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Data
public class OrderDetailDTO {
    private int orderDetailId;
    private int quantity;
    private Integer Size;

    @JsonBackReference(value = "order-detail")
    private OrderDTO order;

    @JsonManagedReference(value = "product-orderDetail")
    private ProductDTO product;
}
