package ma.ensa.movingservice.repositories;

import ma.ensa.movingservice.models.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository
        extends JpaRepository<Rate, Long> {

    @Query("SELECT AVG(r.rate) FROM Rate r WHERE r.service.offer.provider.id = :id")
    double getProviderAverageRate(
            @Param("id") long providerId
    );

}
