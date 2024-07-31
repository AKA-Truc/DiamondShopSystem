package goldiounes.com.vn.repositories;


import goldiounes.com.vn.models.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyRepo extends JpaRepository<Warranty, Integer> {

}
