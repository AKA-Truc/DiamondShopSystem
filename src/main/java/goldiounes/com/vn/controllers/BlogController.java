package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.BlogDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-management")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/blogs")
    public ResponseEntity<ResponseWrapper<BlogDTO>> createBlog(@RequestBody BlogDTO blogDTO) {
        BlogDTO createdBlog = blogService.createBlog(blogDTO);
        ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog created successfully", createdBlog);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<ResponseWrapper<BlogDTO>> getBlogs(@PathVariable int id) {
        BlogDTO blog = blogService.getBlogs(id);
        if (blog != null) {
            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog retrieved successfully", blog);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/blogs")
    public ResponseEntity<ResponseWrapper<List<BlogDTO>>> getAllBlogs() {
        List<BlogDTO> blogs = blogService.getAllBlogs();
        if (!blogs.isEmpty()) {
            ResponseWrapper<List<BlogDTO>> response = new ResponseWrapper<>("Blogs retrieved successfully", blogs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            ResponseWrapper<List<BlogDTO>> response = new ResponseWrapper<>("Blogs not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<ResponseWrapper<BlogDTO>> updateBlog(@PathVariable int id, @RequestBody BlogDTO blogDTO) {
        BlogDTO updatedBlog = blogService.updateBlog(id, blogDTO);
        if (updatedBlog != null) {
            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog updated successfully", updatedBlog);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteBlog(@PathVariable int id) {
        boolean deleted = blogService.deleteBlog(id);
        if (deleted) {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Blog deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>("Blog not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
