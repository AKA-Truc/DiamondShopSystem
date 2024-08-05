//package goldiounes.com.vn.controllers;
//
//import goldiounes.com.vn.dto.CategoryDTO;
//import goldiounes.com.vn.services.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import goldiounes.com.vn.models.entity.Category;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/category-management")
//public class CategoryController {
//    @Autowired
//    private CategoryService categoryService;
//
//    /*@PostMapping("/categories")
//    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
//        Category category = mappingService.convertToCategoryEntity(categoryDTO);
//        Category savedCategory = categoryService.save(category);
//        CategoryDTO responseDTO = mappingService.convertToCategoryDTO(savedCategory);
//        return ResponseEntity.ok(responseDTO);
//    }*/
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
//   /* @GetMapping("/categories/{id}")
//    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
//        Category existingCategory = categoryService.findById(id);
//        if (existingCategory == null) {
//            throw new RuntimeException("Category not found");
//        }
//        CategoryDTO categoryDTO = mappingService.convertToCategoryDTO(existingCategory);
//        return ResponseEntity.ok(categoryDTO);
//    }*/
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
