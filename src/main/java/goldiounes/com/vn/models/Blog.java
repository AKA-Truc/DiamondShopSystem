package goldiounes.com.vn.models;

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

    @Column(name = "Content", nullable = false)
    private String Content;

    public Blog() {
        //cstor
    }

    public Blog(String Title, String Content) {
        this.Title = Title;
        this.Content = Content;
    }

    public int getBlogID() {
        return BlogID;
    }

    public void setBlogID(int blogID) {
        BlogID = blogID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
