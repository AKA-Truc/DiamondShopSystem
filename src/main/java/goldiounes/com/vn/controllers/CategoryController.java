//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.services.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import goldiounes.com.vn.models.Category;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/category-management")
//public class CategoryController {
//    @Autowired
//    private CategoryService categoryService;
//
//    @PostMapping("/categories")
//    private Category createCategory (@RequestBody Category category) {
//        Category existingCategory = categoryService.findByName(category.getCategoryName());
//        if (existingCategory != null) {
//            throw new RuntimeException("Category already exists");
//        }
//        return categoryService.save(category);
//    }
//
//    @GetMapping("/categories")
//    public List<Category> getAll() {
//        List<Category> existingCategory = categoryService.findAll();
//        if (existingCategory == null) {
//            throw new RuntimeException("Category not found");
//        }
//        return existingCategory;
//    }
//
//    @GetMapping("/categories/{id}")
//    private Category getCategoryById(@PathVariable int id) {
//        Category existingCategory = categoryService.findById(id);
//        if (existingCategory == null) {
//            throw new RuntimeException("Category not found");
//        }
//        return existingCategory;
//    }
//
//    @DeleteMapping("/categories/{id}")
//    private void deleteCategory(@PathVariable int id) {
//        Category existingCategory = categoryService.findById(id);
//        if (existingCategory != null) {
//            categoryService.deleteById(id);
//        }
//        throw new RuntimeException("Category not found");
//    }
//
//    @PutMapping("/categories/{id}")
//    private Category updateCategory(@PathVariable int id, @RequestBody Category category) {
//        Category existingCategory = categoryService.findById(id);
//        if (existingCategory == null) {
//            throw new RuntimeException("Category not found");
//        }
//        existingCategory.setCategoryName(category.getCategoryName());
//        return categoryService.save(existingCategory);
//    }
//}

package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.entity.Category;
import goldiounes.com.vn.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-management")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteById(id);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        category.setCategoryID(id);
        return categoryService.save(category);
    }

    @GetMapping("/categories/search")
    public List<Category> searchCategories(@RequestParam String keyword) {
        return categoryService.findCategoryByKeyword(keyword);
    }
}

