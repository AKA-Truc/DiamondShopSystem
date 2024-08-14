package goldiounes.com.vn.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class CategoryDTO {
    private int categoryID;
    private String categoryName;

    @JsonManagedReference
    private List<ProductDTO> products;

    // Getters và setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}


//package goldiounes.com.vn.models.dto;
//
//public class CategoryDTO {
//
//    private int categoryID;
//    private String categoryName;
//
//    public CategoryDTO() {
//    }
//
//    public CategoryDTO(int categoryID, String categoryName) {
//        this.categoryID = categoryID;
//        this.categoryName = categoryName;
//    }
//
//    public int getCategoryID() {
//        return categoryID;
//    }
//
//    public void setCategoryID(int categoryID) {
//        this.categoryID = categoryID;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("CategoryDTO [categoryID=%d, categoryName='%s']", categoryID, categoryName);
//    }
//}
