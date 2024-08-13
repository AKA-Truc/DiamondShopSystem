package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class DiamondDTO {

    private int diamondID;
    private double carat;
    private String color;
    private String clarity;
    private String cut;
    private String origin;

    @JsonIgnoreProperties("diamonds")
    private List<DiamondDetailDTO> diamondDetails;

    @JsonIgnoreProperties("diamond")
    private CertificateDTO certificate;

    public DiamondDTO() {
        // default
    }

    public DiamondDTO(int diamondID, double carat, String color, String clarity, String cut, String origin) {
        this.diamondID = diamondID;
        this.carat = carat;
        this.color = color;
        this.clarity = clarity;
        this.cut = cut;
        this.origin = origin;
    }

    public int getDiamondID() {
        return diamondID;
    }

    public void setDiamondID(int diamondID) {
        this.diamondID = diamondID;
    }

    public double getCarat() {
        return carat;
    }

    public void setCarat(double carat) {
        this.carat = carat;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<DiamondDetailDTO> getDiamondDetails() {
        return diamondDetails;
    }

    public void setDiamondDetails(List<DiamondDetailDTO> diamondDetails) {
        this.diamondDetails = diamondDetails;
    }

    public CertificateDTO getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateDTO certificate) {
        this.certificate = certificate;
    }
}
