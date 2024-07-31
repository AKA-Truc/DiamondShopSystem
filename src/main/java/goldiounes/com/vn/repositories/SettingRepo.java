package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepo extends JpaRepository<Setting, Integer> {
}
