package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.CategoryDTO;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/category-management")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/categories/{id}")
    public CategoryDTO getCategoryById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = "/categories", consumes = "application/json")
    public CategoryDTO createCategory(@RequestBody CategoryDTO category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteById(id);
    }

    @PutMapping("/categories/{id}")
    public CategoryDTO updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @GetMapping("/categories/search")
    public List<CategoryDTO> searchCategories(@RequestParam String keyword) {
        return categoryService.findCategoryByKeyword(keyword);
    }
}

