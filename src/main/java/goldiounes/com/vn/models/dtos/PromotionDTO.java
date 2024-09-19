package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import java.util.Date;

@Data
public class PromotionDTO {
    private int promotionId;
    private String promotionName;
    private String description;
    private Date startDate;
    private Date endDate;
    private int discountPercent;

//    @JsonBackReference(value = "promotions")
    private OrderDTO order;
}
