package goldiounes.com.vn.models.dtos;

import lombok.Data;

@Data
public class CertificateDTO {
    private int certificateID;
    private DiamondDTO diamond;
    private String GIACode;

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public DiamondDTO getDiamond() {
        return diamond;
    }

    public void setDiamond(DiamondDTO diamond) {
        this.diamond = diamond;
    }

    public String getGIACode() {
        return GIACode;
    }

    public void setGIACode(String GIACode) {
        this.GIACode = GIACode;
    }
}
