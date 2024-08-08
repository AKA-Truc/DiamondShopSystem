package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dto.BlogDTO;
import goldiounes.com.vn.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-management")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/blogs")
    public BlogDTO createBlog(@RequestBody BlogDTO blog) {
        return blogService.createBlog(blog);
    }


    @GetMapping("/blogs/{id}")
    public BlogDTO getBlogs(@PathVariable int id) {
        return blogService.getBlogs(id);
    }


    @GetMapping("/blogs")
    public List<BlogDTO> getAllBlogs() {
        return blogService.getAllBlogs();
    }


    @PutMapping("/blogs/{id}")
    public BlogDTO updateBlog(@PathVariable int id, @RequestBody BlogDTO blogDTO) {
        return blogService.updateBlog(id, blogDTO);
    }

    @DeleteMapping("/blogs/{id}")
    public void deleteBlog(@PathVariable int id) {
        blogService.deleteBlog(id);
    }
}