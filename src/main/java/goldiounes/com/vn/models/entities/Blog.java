package goldiounes.com.vn.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BLOGS")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto_increment
    @Column(name = "BlogID")
    private int BlogID;

    @Column(name = "Title", nullable = false)
    private String Title;

    @Column(name = "Url", nullable = true)
    private String Url;

    @Lob
    @Column(name = "Content", nullable = false)
    private String Content;

    public Blog() {
        //constructor
    }

    public Blog(String Title, String Content) {
        this.Title = Title;
        this.Content = Content;
    }
}