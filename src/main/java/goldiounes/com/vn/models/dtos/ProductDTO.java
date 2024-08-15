package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private int productId;
    private String productName;
    private String imageURL;
    private double markupRate;
    private double laborCost;
    private double sellingPrice;
    private double warrantyPeriod;
    private int inventory;

    private CategoryDTO category;

    @JsonIgnore
    private List<ProductDetailDTO> productDetails;

    @JsonIgnore
    private OrderDetailDTO orderDetail;

    @JsonIgnore
    private List<ReceiptDTO> receipts;
}