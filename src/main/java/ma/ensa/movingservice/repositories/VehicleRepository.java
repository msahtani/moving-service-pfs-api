package ma.ensa.movingservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.movingservice.models.Vehicle;
import ma.ensa.movingservice.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VehicleRepository
        extends JpaRepository<Vehicle, String> {


    @Query("SELECT v FROM Vehicle v WHERE v.provider.id = :id")
    List<Vehicle> getAllByProvider(@Param("id") long id);

    @Modifying
    @Query(
            value = "UPDATE vehicle SET verified_by_id = ? WHERE imm = ?",
            nativeQuery = true
    )
    void verifyVehicle(long adminId, long providerId);

    @Modifying
    @Query(
            value = "UPDATE vehicle SET verified_by_id = ? WHERE provider_id = ?",
            nativeQuery = true
    )
    void verifyAllVehicles(long adminId, long providerId);

    @Query("SELECT v.imageName FROM Vehicle v WHERE v.imm = :imm")
    Optional<String> findImageName(
            @Param("imm") String imm
    );

}
