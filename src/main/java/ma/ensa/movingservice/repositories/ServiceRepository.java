package ma.ensa.movingservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.movingservice.enums.ServiceStatus;
import ma.ensa.movingservice.models.Rate;
import ma.ensa.movingservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Modifying
    @Query("UPDATE Service s SET s.status = :status WHERE s.id = :id")
    void setStatus(
            @Param("id") long id,
            @Param("status") ServiceStatus status
    );

    @Modifying
    @Query("UPDATE Service s SET s.rate = :rate WHERE s.id = :id")
    void setRate(
            @Param("id") long id,
            @Param("rate") Rate rate
    );

    @Query("SELECT s FROM Service s WHERE s.status = 'DONE' AND s.offer.provider.id = :id")
    List<Service> findDoneServiceByProvider(
            @Param("id") long providerId
    );

    @Query(
            "SELECT count(s) > 0 FROM Service s " +
            "WHERE s.offer.provider.id = :providerId " +
            "AND s.offer.demand.client.id = :clientId " +
            "AND s.status = 'PENDING'"
    )
    boolean existPendingService(
            @Param("clientId") long clientId,
            @Param("providerId") long providerId
    );
}
