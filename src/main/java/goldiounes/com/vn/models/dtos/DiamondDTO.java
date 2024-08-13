package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class DiamondDTO {
    private int diamondId;
    private double carat;
    private String color;
    private String clarity;
    private String cut;
    private String origin;

   // @JsonManagedReference
    private List<DiamondDetailDTO> diamondDetails;

    //@JsonManagedReference
    private CertificateDTO certificate;
}
