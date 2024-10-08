package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PointDTO {
    private int pointId;
    private int points;

    //@JsonBackReference
    @JsonIgnore
    private UserDTO user;
}

