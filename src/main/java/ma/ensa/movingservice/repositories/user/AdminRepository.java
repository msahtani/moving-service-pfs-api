package ma.ensa.movingservice.repositories.user;

import ma.ensa.movingservice.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {}