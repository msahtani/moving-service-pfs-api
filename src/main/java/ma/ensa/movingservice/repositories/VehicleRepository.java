package ma.ensa.movingservice.repositories;

import ma.ensa.movingservice.models.Vehicle;
import ma.ensa.movingservice.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository
        extends JpaRepository<Vehicle, String> {


    @Query("SELECT v FROM Vehicle v WHERE v.provider.id = :id")
    List<Vehicle> getAllByProvider(@Param("id") long id);

    @Modifying
    @Query("UPDATE Vehicle v SET v.verifiedBy = :admin WHERE v.imm = :imm")
    void verifyVehicle(
            @Param("imm")   String imm,
            @Param("admin") Admin admin
            );

    @Modifying
    @Query("UPDATE Vehicle v SET v.verifiedBy = :admin WHERE v.provider.id = :id")
    void verifyAllVehicles(
            @Param("id") long providerId,
            @Param("admin") Admin admin
    );

}
