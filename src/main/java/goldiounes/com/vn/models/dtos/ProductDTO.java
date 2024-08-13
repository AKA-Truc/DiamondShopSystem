package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private int productId;
    private String productName;
    private String imageURL;
    private Integer size;
    private double markupRate;
    private double laborCost;
    private double sellingPrice;
    private double warrantyPeriod;
    private int inventory;

    private CategoryDTO category;

   // @JsonManagedReference
    private List<ProductDetailDTO> productDetails;

    //@JsonBackReference
    private OrderDetailDTO orderDetail;

    //@JsonManagedReference
    private List<ReceiptDTO> receipts;
}
