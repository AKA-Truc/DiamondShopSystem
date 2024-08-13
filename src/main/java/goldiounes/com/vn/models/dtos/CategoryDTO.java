package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private int categoryId;
    private String categoryName;

    //@JsonBackReference
    private List<ProductDTO> products;

}