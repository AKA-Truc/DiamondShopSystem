package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

public class WarrantyDTO {

    private int WarrantyID;

    @JsonIgnoreProperties("warranties")
    private ProductDTO Product;

    @JsonIgnoreProperties("warranties")
    private UserDTO User;

    private String WarrantyDetails;
    private Date StartDate;
    private Date EndDate;

    public WarrantyDTO() {
        // default constructor
    }

    public WarrantyDTO(int WarrantyID, ProductDTO Product, UserDTO User, String WarrantyDetails, Date StartDate, Date EndDate) {
        this.WarrantyID = WarrantyID;
        this.Product = Product;
        this.User = User;
        this.WarrantyDetails = WarrantyDetails;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
    }

    // Getters and Setters

    public int getWarrantyID() {
        return WarrantyID;
    }

    public void setWarrantyID(int WarrantyID) {
        this.WarrantyID = WarrantyID;
    }

    public ProductDTO getProduct() {
        return Product;
    }

    public void setProduct(ProductDTO Product) {
        this.Product = Product;
    }

    public UserDTO getUser() {
        return User;
    }

    public void setUser(UserDTO User) {
        this.User = User;
    }

    public String getWarrantyDetails() {
        return WarrantyDetails;
    }

    public void setWarrantyDetails(String WarrantyDetails) {
        this.WarrantyDetails = WarrantyDetails;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }
}
