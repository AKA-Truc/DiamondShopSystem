package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SettingDTO {

    private int settingID;
    private String material;
    private int price;

    @JsonBackReference
    private ProductDetailDTO productDetails;

    public SettingDTO() {
        // Default constructor
    }

    public SettingDTO(int settingID, String material, int price) {
        this.settingID = settingID;
        this.material = material;
        this.price = price;
    }

    public int getSettingID() {return settingID;}

    public void setSettingID(int settingID) {this.settingID = settingID;}

    public String getMaterial() {return material;}

    public void setMaterial(String material) {this.material = material;}

    public int getPrice() {return price;}

    public void setPrice(int price) {this.price = price;}

    public ProductDetailDTO getProductDetails() {return productDetails;}

    public void setProductDetails(ProductDetailDTO productDetails) {this.productDetails = productDetails;}
}
