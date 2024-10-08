package goldiounes.com.vn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import goldiounes.com.vn.models.dtos.BlogDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/blog-management")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/blogs")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<BlogDTO>> createBlog(
            @RequestParam("blog") String blogDTOJson,
            @RequestParam("imageURL") MultipartFile imageFile) {

        try {
            BlogDTO blogDTO = objectMapper.readValue(blogDTOJson, BlogDTO.class);

            BlogDTO createdBlog = blogService.createBlog(blogDTO, imageFile);

            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog created successfully", createdBlog);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Error creating blog: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<BlogDTO>> updateBlog(
            @PathVariable int id,
            @RequestParam("blog") String blogDTOJson,
            @RequestParam(value = "imageURL", required = false) MultipartFile imageFile) {

        try {
            BlogDTO blogDTO = objectMapper.readValue(blogDTOJson, BlogDTO.class);

            BlogDTO updatedBlog = blogService.updateBlog(id, blogDTO, imageFile);

            if (updatedBlog != null) {
                ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog updated successfully", updatedBlog);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Blog not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            ResponseWrapper<BlogDTO> response = new ResponseWrapper<>("Error updating blog: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/blogs/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
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
