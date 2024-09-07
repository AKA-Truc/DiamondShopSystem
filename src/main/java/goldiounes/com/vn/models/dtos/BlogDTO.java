package goldiounes.com.vn.models.dtos;

import lombok.Data;

@Data
public class BlogDTO {
    private int blogId;
    private String title;
    private String url;
    private String content;
}