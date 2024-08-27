package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.CategoryDTO;
import goldiounes.com.vn.responses.ResponseWrapper;
import goldiounes.com.vn.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-management")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF')")
    public ResponseEntity<ResponseWrapper<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        if(!categories.isEmpty()) {
            ResponseWrapper<List<CategoryDTO>> response = new ResponseWrapper<>("Category retrieved successfully", categories);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            ResponseWrapper<List<CategoryDTO>> response = new ResponseWrapper<>("Category is not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/categories/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF', 'ROLE_CUSTOMER', 'ROLE_GUEST')")
    public ResponseEntity<ResponseWrapper<CategoryDTO>> getCategoryById(@PathVariable int id) {
        CategoryDTO category = categoryService.findById(id);
        ResponseWrapper<CategoryDTO> response;

        if (category != null) {
            response = new ResponseWrapper<>("Category retrieved successfully", category);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Category not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/categories")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<CategoryDTO>> createCategory(@RequestBody CategoryDTO category) {
        CategoryDTO createdCategory = categoryService.createCategory(category);
        ResponseWrapper<CategoryDTO> response = new ResponseWrapper<>("Category created successfully", createdCategory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<String>> deleteCategory(@PathVariable int id) {
        boolean isDeleted = categoryService.deleteById(id);
        ResponseWrapper<String> response;
        if (isDeleted) {
            response = new ResponseWrapper<>("Category deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseWrapper<>("Category not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<ResponseWrapper<CategoryDTO>> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        ResponseWrapper<CategoryDTO> response;

        if (updatedCategory != null) {
            response = new ResponseWrapper<>("Category updated successfully", updatedCategory);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("Category not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/categories/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SALES_STAFF', 'ROLE_DELIVERY_STAFF')")
    public ResponseEntity<ResponseWrapper<List<CategoryDTO>>> searchCategories(@RequestParam String keyword) {
        List<CategoryDTO> categories = categoryService.findCategoryByKeyword(keyword);
        ResponseWrapper<List<CategoryDTO>> response;

        if (!categories.isEmpty()) {
            response = new ResponseWrapper<>("Categories found", categories);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new ResponseWrapper<>("No categories found with the given keyword", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

