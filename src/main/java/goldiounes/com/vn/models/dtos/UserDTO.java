package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String role;

    //@JsonManagedReference
    @JsonIgnore
    private List<WarrantyDTO> warranties;

    //@JsonBackReference
    @JsonIgnore
    private List<OrderDTO> orders;

    //@JsonManagedReference
    private PointDTO point;

    //@JsonBackReference
    @JsonIgnore
    private CartDTO cart;
}