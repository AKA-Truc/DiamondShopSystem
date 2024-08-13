package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.BlogDTO;
import goldiounes.com.vn.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog-management")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/blogs")
    public ResponseEntity<Map<String, Object>> createBlog(@RequestBody BlogDTO blog) {
        BlogDTO createdBlog = blogService.createBlog(blog);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Blog created successfully");
        response.put("blog", createdBlog);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Map<String, Object>> getBlogs(@PathVariable int id) {
        BlogDTO blog = blogService.getBlogs(id);
        if (blog != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Blog retrieved successfully");
            response.put("blog", blog);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Blog not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/blogs")
    public ResponseEntity<Map<String, Object>> getAllBlogs() {
        List<BlogDTO> blogs = blogService.getAllBlogs();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Blogs retrieved successfully");
        response.put("blogs", blogs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Map<String, Object>> updateBlog(@PathVariable int id, @RequestBody BlogDTO blogDTO) {
        BlogDTO updatedBlog = blogService.updateBlog(id, blogDTO);
        if (updatedBlog != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Blog updated successfully");
            response.put("blog", updatedBlog);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Blog not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Map<String, Object>> deleteBlog(@PathVariable int id) {
        boolean deleted = blogService.deleteBlog(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Blog deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Blog not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
