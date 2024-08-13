package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private int cartId;

    //@JsonManagedReference
    private UserDTO user;

    //@JsonManagedReference
    private List<OrderDTO> orders;

    //@JsonManagedReference
    private List<CartItemDTO> cartItems;
}