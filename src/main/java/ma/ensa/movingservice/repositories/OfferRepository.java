package ma.ensa.movingservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.movingservice.models.Offer;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.user.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface OfferRepository
        extends JpaRepository<Offer, Long> {


    @Query("SELECT COUNT(o) FROM Offer o WHERE o.provider = :provider")
    int findByProvider(@Param("provider") Provider provider);

    @Modifying
    @Query("UPDATE Offer o SET o.service = :service WHERE o.id = :id")
    int setServiceById(
            @Param("id") long id,
            @Param("service") Service service
    );

    @Query("SELECT o FROM Offer o WHERE o.demand.id = :id")
    List<Offer> findByDemandId(@Param("id") long demandId);
}
