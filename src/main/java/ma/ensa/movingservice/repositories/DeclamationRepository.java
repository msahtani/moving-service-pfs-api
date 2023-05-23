package ma.ensa.movingservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.movingservice.models.Declamation;
import ma.ensa.movingservice.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface DeclamationRepository
    extends JpaRepository<Declamation, Long> {


    @Modifying
    @Query("UPDATE Declamation d SET d.closedBy = :admin WHERE d.id = :id")
    void close(
            @Param("id") long id,
            @Param("admin") Admin admin
    );
}
