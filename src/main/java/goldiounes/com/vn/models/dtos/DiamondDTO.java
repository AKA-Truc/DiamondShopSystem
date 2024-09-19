package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class DiamondDTO {
    private int diamondId;
    private String GIACode;
    private double measurement;
    private double carat;
    private String color;
    private String clarity;
    private String cut;
    private String shape;
    private int price;

    @JsonIgnore
    private List<DiamondDetailDTO> diamondDetails;
}
