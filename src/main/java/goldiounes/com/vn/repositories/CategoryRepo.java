package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.CategoryName=:CategoryName")
    Category findByName(@Param("CategoryName") String CategoryName);

    @Query("SELECT c FROM Category c WHERE c.CategoryName LIKE %:keyword%")
    List<Category> findCategoryByKeyword(@Param("keyword") String keyword);
}
