package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private int productId;
    private String productName;
    private String Status;
    private String imageURL;
    private String SubImageURL;
    private double warrantyPeriod;


    private CategoryDTO category;

    @JsonIgnore
    private List<ProductDetailDTO> productDetails;

    @JsonBackReference(value = "product-orderDetail")
    private OrderDetailDTO orderDetail;

    @JsonManagedReference(value = "product-warranty")
    private List<WarrantyDTO> warranties;

}