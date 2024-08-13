package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private int orderId;
    private int totalPrice;
    private String status;
    private String shippingAddress;

   // @JsonManagedReference
    private UserDTO user;

    //@JsonBackReference
    private CartDTO cart;

    //@JsonManagedReference
    private PromotionDTO promotion;

    //@JsonManagedReference
    private List<OrderDetailDTO> orderDetails;
}
