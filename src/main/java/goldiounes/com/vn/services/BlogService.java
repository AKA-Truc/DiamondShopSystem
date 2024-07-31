package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Blog;
import goldiounes.com.vn.models.Category;
import goldiounes.com.vn.repositories.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;

    public List<Blog> findAll() {
        return blogRepo.findAll();
    }
    public Blog findById(int id) {
        return blogRepo.findById(id).get();
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
