package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ReceiptDTO {

    private int ReceiptID;

    private int Quantity;

    @JsonIgnoreProperties("receipts")
    private ProductDTO product;

    public ReceiptDTO() {
        // default constructor
    }

    public ReceiptDTO(int receiptID, ProductDTO product, int quantity) {
        ReceiptID = receiptID;
        Quantity = quantity;
        this.product = product;
    }

    public int getReceiptID() {return ReceiptID;}

    public void setReceiptID(int receiptID) {ReceiptID = receiptID;}

    public int getQuantity() {return Quantity;}

    public void setQuantity(int quantity) {Quantity = quantity;}

    public ProductDTO getProduct() {return product;}

    public void setProduct(ProductDTO product) {this.product = product;}
}