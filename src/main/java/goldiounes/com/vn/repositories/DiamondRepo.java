package goldiounes.com.vn.repositories;

import goldiounes.com.vn.models.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiamondRepo extends JpaRepository<Diamond, Integer> {

    @Query("select d from Diamond d where d.Carat=:carat and d.Clarity=:clarity and d.Color=:color and d.Cut=:cut and d.Origin=:origin")
    Diamond findDiamond(@Param("carat") Double carat,
                        @Param("clarity") String clarity,
                        @Param("color") String color,
                        @Param("cut") String cut,
                        @Param("origin") String origin);
}
