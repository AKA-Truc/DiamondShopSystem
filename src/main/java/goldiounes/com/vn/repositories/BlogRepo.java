package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Blog;
import goldiounes.com.vn.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {

    @Query("select b from Blog b where b.Title=:title")
    Blog findByTitle(@Param("title") String title);
}
