package ma.ensa.movingservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Declamation {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User declaimer;

    @ManyToOne
    private User declaimed;

    private String description;

    @ManyToOne
    private Admin closedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
