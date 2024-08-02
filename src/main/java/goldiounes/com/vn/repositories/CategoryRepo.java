package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.CategoryName=:CategoryName")
    Category findByName(@Param("CategoryName") String CategoryName);

}
