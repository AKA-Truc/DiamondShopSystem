//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.models.entity.Blog;
//import goldiounes.com.vn.services.BlogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/blog-management")
//public class BlogController {
//
//    @Autowired
//    private BlogService blogService;
//
//    @PostMapping("/blogs")
//    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
//        Blog existingBlog = blogService.findByName(blog.getTitle());
//        if (existingBlog != null) {
//            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
//        }
//        Blog savedBlog = blogService.save(blog);
//        return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/blogs/{id}")
//    public ResponseEntity<Blog> getBlogById(@PathVariable int id) {
//        Blog existingBlog = blogService.findById(id);
//        if (existingBlog == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(existingBlog, HttpStatus.OK);
//    }
//
//    @GetMapping("/blogs")
//    public ResponseEntity<List<Blog>> getAllBlogs() {
//        List<Blog> blogs = blogService.findAll();
//        if (blogs.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(blogs, HttpStatus.OK);
//    }
//
//    @PutMapping("/blogs/{id}")
//    public ResponseEntity<Blog> updateBlog(@PathVariable int id, @RequestBody Blog blog) {
//        Blog existingBlog = blogService.findById(id);
//        if (existingBlog == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        existingBlog.setTitle(blog.getTitle());
//        existingBlog.setContent(blog.getContent());
//        Blog updatedBlog = blogService.save(existingBlog);
//        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/blogs/{id}")
//    public ResponseEntity<HttpStatus> deleteBlog(@PathVariable int id) {
//        Blog existingBlog = blogService.findById(id);
//        if (existingBlog == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        blogService.deleteById(existingBlog.getBlogID());
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
