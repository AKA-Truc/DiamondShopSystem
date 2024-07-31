package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepo extends JpaRepository<Certificate, Integer> {

}
