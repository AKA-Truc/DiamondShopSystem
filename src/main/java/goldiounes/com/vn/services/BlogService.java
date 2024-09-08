package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.BlogDTO;
import goldiounes.com.vn.models.entities.Blog;
import goldiounes.com.vn.repositories.BlogRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ModelMapper modelMapper;


    public List<BlogDTO> getAllBlogs() {
        List<Blog> blogs = blogRepo.findAll();
        if (blogs.isEmpty()) {
            throw new RuntimeException("No blogs found");
        }
        return modelMapper.map(blogs, new TypeToken<List<BlogDTO>>() {}.getType());
    }


    public BlogDTO getBlogs(int id) {
        Optional<Blog> existingBlog = blogRepo.findById(id);
        if (existingBlog.isEmpty()) {
            throw new RuntimeException("No blog found");
        }
        return modelMapper.map(existingBlog.get(), new TypeToken<BlogDTO>() {}.getType());
    }


    public BlogDTO createBlog(BlogDTO blogDTO, MultipartFile url) throws IOException {
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        Blog existingBlog = blogRepo.findByTitle(blog.getTitle());
        if (existingBlog != null) {
            throw new RuntimeException("Blog already exists ");
        }
        if (url != null && !url.isEmpty()) {
            String imageURL = fileUploadService.uploadImage(url);
            blog.setUrl(imageURL);
        }
        blogRepo.save(blog);
        return modelMapper.map(blog, BlogDTO.class);
    }

    public boolean deleteBlog(int id) {
        Blog existingBlog = blogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No blog found"));
        blogRepo.deleteById(existingBlog.getBlogID());
        return true;
    }

    public BlogDTO updateBlog(int id, BlogDTO blogDTO, MultipartFile url) throws IOException {
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        Blog existingBlog = blogRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No blog found"));
        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        if (url != null && !url.isEmpty()) {
            String imageURL = fileUploadService.uploadImage(url);
            blog.setUrl(imageURL);
        }
        blogRepo.save(existingBlog);
        return modelMapper.map(existingBlog, new TypeToken<BlogDTO>() {}.getType());
    }
}