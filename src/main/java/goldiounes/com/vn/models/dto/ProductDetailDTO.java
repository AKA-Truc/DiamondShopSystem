package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class ProductDetailDTO{
    private int productDetailDTO;
    private int productID;
    private String productName;
    private String laborCost;
    private int productDetail;

    public int getProductDetailDTO() {
        return productDetailDTO;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getLaborCost() {
        return laborCost;
    }

    public int getProductDetail() {
        return productDetail;
    }

    public void setProductDetailDTO(int productDetailDTO) {
        this.productDetailDTO = productDetailDTO;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setLaborCost(String laborCost) {
        this.laborCost = laborCost;
    }

    public void setProductDetail(int productDetail) {
        this.productDetail = productDetail;
    }
}

