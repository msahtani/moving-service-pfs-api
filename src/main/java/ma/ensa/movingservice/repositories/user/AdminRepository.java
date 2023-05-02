package ma.ensa.movingservice.repositories.user;

import ma.ensa.movingservice.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {


    @Query("DELETE FROM Admin a WHERE a.id = :id AND NOT a.sudo")
    int deleteById(
            @Param("id") long id
    );
}
