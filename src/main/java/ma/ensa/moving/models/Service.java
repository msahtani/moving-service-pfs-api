package ma.ensa.moving.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.moving.enums.ServiceStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Service {

    @Id
    @OneToOne
    private Offer offer;

    private String sTime;

    private String eTime;

    @Enumerated(value = EnumType.STRING)
    private ServiceStatus status;

}
