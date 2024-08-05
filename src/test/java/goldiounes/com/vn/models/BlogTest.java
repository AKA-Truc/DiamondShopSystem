//package goldiounes.com.vn.models;
//
//import goldiounes.com.vn.controllers.BlogController;
//import goldiounes.com.vn.services.BlogService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class BlogTest {
//
//    @Mock
//    private BlogService blogService;
//
//    @InjectMocks
//    private BlogController blogController;
//
//    public BlogTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateBlog_Conflict() {
//        Blog blog = new Blog("Test Title", "Test Content");
//        when(blogService.findByName(blog.getTitle())).thenReturn(blog);
//
//        ResponseEntity<Blog> response = blogController.createBlog(blog);
//
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals(null, response.getBody());
//    }
//
//    @Test
//    public void testGetBlogById_Success() {
//        Blog blog = new Blog("Test Title", "Test Content");
//        when(blogService.findById(1)).thenReturn(blog);
//
//        ResponseEntity<Blog> response = blogController.getBlogById(1);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(blog, response.getBody());
//    }
//
//    @Test
//    public void testDeleteBlog_NotFound() {
//        when(blogService.findById(1)).thenReturn(null);
//
//        ResponseEntity<HttpStatus> response = blogController.deleteBlog(1);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
//}
