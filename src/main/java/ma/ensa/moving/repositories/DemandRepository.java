package ma.ensa.moving.repositories;

import ma.ensa.moving.models.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemandRepository extends JpaRepository<Demand, Long> {

    @Query(
            "SELECT d FROM Demand d WHERE d.sCity= :city " +
            "AND d NOT IN (SELECT s.offer.demand FROM Service s)"
    )
    List<Demand> findForProvider(String city);

}
