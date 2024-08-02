package goldiounes.com.vn.repositories;


import goldiounes.com.vn.models.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantyRepo extends JpaRepository<Warranty, Integer> {

    @Query ("select u from User u where u.UserID=:userid")
    public List<Warranty> findByUserID(@Param("userid") int userid);
}
