package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepo extends JpaRepository<Certificate, Integer> {

    @Query("select c from Certificate c where c.GIACode=:giaCode")
    Certificate findByGIACode(@Param("giaCode") String giaCode);

    Optional<Object> findByDiamondId(int diamondId);
}
