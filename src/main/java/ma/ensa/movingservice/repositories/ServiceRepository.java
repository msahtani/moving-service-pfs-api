package ma.ensa.movingservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.movingservice.enums.ServiceStatus;
import ma.ensa.movingservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Modifying
    @Query("UPDATE Service s SET s.status = :status WHERE s.id = :id")
    void setStatus(
            @Param("id") long id,
            @Param("status") ServiceStatus status
    );


}
