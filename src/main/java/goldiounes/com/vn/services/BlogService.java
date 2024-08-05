package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entity.Blog;
import goldiounes.com.vn.repositories.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;

    public List<Blog> findAll() {
        return blogRepo.findAll();
    }

    public Blog findById(int id) {
        Optional<Blog> blog = blogRepo.findById(id);
        return blog.orElse(null);
    }

    public Blog save(Blog blog) {
        return blogRepo.save(blog);
    }

    public void deleteById(int id) {
        blogRepo.deleteById(id);
    }

    public Blog findByName(String title) {
        return blogRepo.findByTitle(title);
    }
}
