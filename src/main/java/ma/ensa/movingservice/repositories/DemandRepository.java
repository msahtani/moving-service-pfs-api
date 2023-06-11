package ma.ensa.movingservice.repositories;

import ma.ensa.movingservice.models.Demand;
import ma.ensa.movingservice.models.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface DemandRepository extends JpaRepository<Demand, Long> {

    @Query( "SELECT d FROM Demand d WHERE d.sCity= :city " +
            "AND d NOT IN (SELECT s.offer.demand FROM Service s " +
            "WHERE s.status = 'DONE' )"
    )
    List<Demand> findForProvider(String city);

    List<Demand> findAllByClient(Client client);
}
