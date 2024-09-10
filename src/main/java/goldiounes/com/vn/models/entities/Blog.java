package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BLOGS")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tùy chỉnh lại nếu bạn sử dụng AUTO_INCREMENT
    @Column(name = "BlogID")
    private int BlogID;

    @Column(name = "Title", nullable = false)
    private String Title;

    @Column(name = "Url")
    private String Url;

    @Lob
    @Column(name = "Content", nullable = false, columnDefinition = "Text") // Explicitly define as CLOB for text content
    private String Content;

    public Blog() {
        // Default constructor
    }

    public Blog(String Title, String Content) {
        this.Title = Title;
        this.Content = Content;
    }
}
