package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.Date;

@Data
public class WarrantyDTO {
    private int warrantyId;
    private String warrantyDetails;
    private Date startDate;
    private Date endDate;

    //@JsonBackReference
    private ProductDTO product;

    //@JsonBackReference
    private UserDTO user;
}
