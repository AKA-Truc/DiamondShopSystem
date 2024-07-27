package goldiounes.com.vn.controllers;

import goldiounes.com.vn.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import goldiounes.com.vn.models.Category;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public Category createCategory(@RequestBody Category category) {
        Category existingCategory = categoryService.findByName(category.getCategoryName());
        if (existingCategory == null) {
            return categoryService.save(category);
        }
        throw new RuntimeException("Category already exists");
    }

    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable("id") Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return category;
    }

    @GetMapping("/getAll")
    public List<Category> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        if (categories == null || categories.isEmpty()) {
            throw new RuntimeException("List categories is empty");
        }
        return categories;
    }

    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
        Category existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            throw new RuntimeException("Category not found");
        }
        existingCategory.setCategoryName(category.getCategoryName());
        return categoryService.save(existingCategory);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategoryById(@PathVariable("id") Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        categoryService.delete(category);
    }
}
