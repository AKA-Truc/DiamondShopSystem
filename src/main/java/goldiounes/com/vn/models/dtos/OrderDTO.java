package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private int orderId;
    private int totalPrice;
    private String status;
    private String shippingAddress;
    private Date startDate;

    // @JsonManagedReference
    private UserDTO user;

    //@JsonBackReference
    @JsonIgnore
    private CartDTO cart;

    //@JsonManagedReference
    private PromotionDTO promotion;

    //@JsonManagedReference
    private List<OrderDetailDTO> orderDetails;
}
