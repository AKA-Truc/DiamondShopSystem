package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class CertificateDTO {
    private int certificateId;
    private String GIACode;

    //@JsonBackReference
    private DiamondDTO diamond;
}
