package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Data
public class OrderDetailDTO {
    private int orderDetailId;
    private int quantity;
    private double price;

    //@JsonBackReference
    @JsonIgnore
    private OrderDTO order;

    //@JsonManagedReference
    private ProductDTO product;
}
