package ma.ensa.moving.repositories.user;

import ma.ensa.moving.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
