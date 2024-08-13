package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class PointDTO {
    private int pointId;
    private int points;

    //@JsonBackReference
    private UserDTO user;
}

