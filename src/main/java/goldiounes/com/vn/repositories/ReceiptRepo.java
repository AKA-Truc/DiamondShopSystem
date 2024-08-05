package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepo extends JpaRepository<Receipt, Integer> {
}
