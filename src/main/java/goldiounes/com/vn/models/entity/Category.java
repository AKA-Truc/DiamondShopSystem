package goldiounes.com.vn.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "CategoryID")
    private int CategoryID;

    @Column(name = "CategoryName", nullable = false, unique = false)
    private String CategoryName;

    @OneToMany(mappedBy = "Category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Category() {
        //cstor
    }

    public Category(String categoryName) {
        this.CategoryName = categoryName;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}