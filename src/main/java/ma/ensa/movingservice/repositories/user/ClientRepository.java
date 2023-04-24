package ma.ensa.movingservice.repositories.user;

import ma.ensa.movingservice.models.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
