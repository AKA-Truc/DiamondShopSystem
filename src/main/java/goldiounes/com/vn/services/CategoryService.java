package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entity.Category;
import goldiounes.com.vn.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    public List<Category> findAll() {
        List<Category> categories = categoryRepo.findAll();
        if (categories == null ) {
            throw new RuntimeException("No categories found");
        }
        return categories;
    }

    public Category findById(int id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new RuntimeException("Category not found");
        }
        return optionalCategory.get();
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public void deleteById(int id) {
        if (!categoryRepo.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepo.deleteById(id);
    }

    public Category findByName(String name) {
        Category category = categoryRepo.findByName(name);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return category;
    }

    public List<Category> findCategoryByKeyword(String KeyWord) {
        return categoryRepo.findCategoryByKeyword(KeyWord);
    }
}
