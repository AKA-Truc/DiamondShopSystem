package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CategoryDTO;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.repositories.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    private CategoryService categoryService;
    private Category category;
    private CategoryDTO categoryDTO;
    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        categoryRepo = Mockito.mock(CategoryRepo.class);
        modelMapper = new ModelMapper();
        categoryService = new CategoryService(categoryRepo, modelMapper);

        category = new Category("Nhẫn Bạc");

        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Nhẫn Bạc");

    }

    @Test
    public void testCreateCategory_CategoryAlreadyExists() {
        when(categoryRepo.findByName(category.getCategoryName())).thenReturn(category);

        assertThrows(RuntimeException.class, () -> categoryService.createCategory(categoryDTO));

        verify(categoryRepo, never()).save(any(Category.class));
    }

    @Test
    public void testCreateCategory_NewCategory() {
        when(categoryRepo.findByName(category.getCategoryName())).thenReturn(null);
        when(categoryRepo.save(any(Category.class))).thenReturn(category);

        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);

        assertNotNull(savedCategoryDTO);
        assertEquals(categoryDTO.getCategoryName(), savedCategoryDTO.getCategoryName());

        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        int categoryId = 1;
        Category updatedCategory = new Category("Nhẫn Vàng");
        categoryDTO.setCategoryName("Nhẫn Vàng");

        when(categoryRepo.findById(categoryId)).thenReturn(java.util.Optional.of(category));
        when(categoryRepo.save(any(Category.class))).thenReturn(updatedCategory);

        // Act
        CategoryDTO result = categoryService.updateCategory(categoryId, categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Nhẫn Vàng", result.getCategoryName());
        verify(categoryRepo, times(1)).findById(categoryId);
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        int categoryId = 0;

        when(categoryRepo.findById(categoryId)).thenReturn(java.util.Optional.of(category));

        boolean result = categoryService.deleteById(categoryId);

        assertTrue(result);
        verify(categoryRepo, times(1)).findById(categoryId);
        verify(categoryRepo, times(1)).deleteById(categoryId);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = List.of(category, new Category("Vòng Tay"));
        when(categoryRepo.findAll()).thenReturn(categories);

        List<CategoryDTO> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepo, times(1)).findAll();
    }

    @Test
    public void testGetCategoryById() {
        int categoryId = 1;
        when(categoryRepo.findById(categoryId)).thenReturn(java.util.Optional.of(category));

        CategoryDTO result = categoryService.findById(categoryId);

        assertNotNull(result);
        assertEquals("Nhẫn Bạc", result.getCategoryName());
        verify(categoryRepo, times(1)).findById(categoryId);
    }

    @Test
    public void testGetCategoryByName() {
        String categoryName = "Nhẫn Bạc";
        when(categoryRepo.findByName(categoryName)).thenReturn(category);

        CategoryDTO result = categoryService.findByName(categoryName);

        assertNotNull(result);
        assertEquals(categoryName, result.getCategoryName());
        verify(categoryRepo, times(1)).findByName(categoryName);
    }

}
