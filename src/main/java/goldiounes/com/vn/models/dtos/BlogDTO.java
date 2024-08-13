package goldiounes.com.vn.models.dtos;


public class BlogDTO {
    private int BlogId;
    private String Title;
    private String Content;

    public BlogDTO() {
        //default constructor
    }
    public BlogDTO(int blogId, String title, String content) {
        BlogId = blogId;
        Title = title;
        Content = content;
    }
    public int getBlogId() {return BlogId;}
    public String getTitle() {return Title;}
    public String getContent() {return Content;}

    public void setBlogId(int blogId) {this.BlogId = blogId;}
    public void setTitle(String title) {this.Title = title;}
    public void setContent(String content) {this.Content = content;}
}