package ma.ensa.movingservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Signal {

    @Id
    // ....
    private long id;

    @ManyToOne
    private User signalant; //

    // ....

}
