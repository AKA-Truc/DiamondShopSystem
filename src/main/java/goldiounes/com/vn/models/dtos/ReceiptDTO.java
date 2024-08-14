package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class ReceiptDTO {
    private int receiptId;
    private int quantity;

    //@JsonBackReference
    private ProductDTO product;

}