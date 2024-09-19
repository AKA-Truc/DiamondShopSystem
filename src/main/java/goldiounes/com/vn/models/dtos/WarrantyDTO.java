package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.Date;

@Data
public class WarrantyDTO {
    private int warrantyId;
    private Date endDate;

    @JsonBackReference(value = "product-warranty")
    private ProductDTO product;

    //@JsonBackReference
    private UserDTO user;
}
