package ma.ensa.moving.repositories.user;

import ma.ensa.moving.models.user.Admin;
import ma.ensa.moving.models.user.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    @Modifying
    @Query(value = "UPDATE Provider p SET p.acceptedBy = :admin WHERE p.id = :id")
    int acceptProvider(
            @Param("id") long id,
            @Param("admin") Admin admin
    );
}
