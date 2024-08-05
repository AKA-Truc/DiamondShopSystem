package goldiounes.com.vn.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class CategoryDTO {
    private int categoryID;
    private String categoryName;

    @JsonIgnoreProperties("category")
    private List<ProductDTO> products;

    public CategoryDTO() {
        //default
    }

    public CategoryDTO(int categoryID, String categoryName, List<ProductDTO> products) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.products = products;
    }

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
