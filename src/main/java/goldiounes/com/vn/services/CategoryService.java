package goldiounes.com.vn.services;

import goldiounes.com.vn.models.dtos.CategoryDTO;
import goldiounes.com.vn.models.entities.Category;
import goldiounes.com.vn.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryService(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found");
        } else {
            return modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());
        }
    }

    public CategoryDTO findById ( int id){
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No category found"));
        return modelMapper.map(existingCategory, new TypeToken<CategoryDTO>() {}.getType());
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        if(categoryRepo.findByName(newCategory.getCategoryName()) != null){
            throw new RuntimeException("Category name already exists");
        }
        categoryRepo.save(newCategory);
        return modelMapper.map(newCategory, new TypeToken<CategoryDTO>(){}.getType());
    }

    public boolean deleteById (int id){
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No category found"));
        categoryRepo.deleteById(existingCategory.getCategoryId());
        return true;
    }

    public CategoryDTO findByName (String name){
        Category existingCategory = categoryRepo.findByName(name);
        if(existingCategory == null) {
            throw new RuntimeException("No category found");
        }
        else {
            return modelMapper.map(existingCategory, new TypeToken<CategoryDTO>() {}.getType());
        }
    }

    public List<CategoryDTO> findCategoryByKeyword (String KeyWord) {
        List<Category> categories = categoryRepo.findCategoryByKeyword(KeyWord);
        if (categories.isEmpty()) {
            throw new RuntimeException("No category found");
        }
        return modelMapper.map(categories,new TypeToken<List<CategoryDTO>>() {}.getType());
    }

    public CategoryDTO updateCategory (int id,CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No category found"));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepo.save(existingCategory);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }

}