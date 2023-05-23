package ma.ensa.movingservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.enums.ServiceStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Service {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "service")
    private Offer offer;

    private String sTime;

    private String eTime;

    @Enumerated(value = EnumType.STRING)
    private ServiceStatus status;

    @OneToOne
    private Rate rate;

}
