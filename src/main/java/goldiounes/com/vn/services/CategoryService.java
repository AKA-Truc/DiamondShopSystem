package goldiounes.com.vn.services;

import goldiounes.com.vn.models.Category;
import goldiounes.com.vn.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }
    public Category findById(int id) {
        return categoryRepo.findById(id).get();
    }
    public Category save(Category category) {
        return categoryRepo.save(category);
    }
    public void delete(Category category) {
        categoryRepo.delete(category);
    }
    public void deleteById(int id) {
        categoryRepo.deleteById(id);
    }
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }
}
