package ma.ensa.movingservice.repositories.user;

import jakarta.transaction.Transactional;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProviderRepository extends JpaRepository<Provider, Long> {


    List<Provider> findAllByAcceptedByIsNull();

    @Query("SELECT COUNT(*) FROM Provider WHERE id = :id AND acceptedBy IS NOT NULL")
    long isVerified(@Param("id") long providerId);


    @Modifying
    @Query(
            value = "UPDATE provider SET accepted_by_id = ? WHERE id = ?",
            nativeQuery = true
    )
    void acceptProvider(long id0, long id1);
}
