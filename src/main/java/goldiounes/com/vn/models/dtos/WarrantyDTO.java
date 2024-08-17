package goldiounes.com.vn.models.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class WarrantyDTO {
    private int warrantyId;
    private Date endDate;

    //@JsonBackReference
    private ProductDTO product;

    //@JsonBackReference
    private UserDTO user;
}
