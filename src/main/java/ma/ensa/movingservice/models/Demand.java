package ma.ensa.movingservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.user.Client;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Demand {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Client client;

    private String title;

    private String description;

    private String sCity;

    private String dCity;

    private LocalDateTime approxTime;

    private Double proposedPrice;

    private LocalDateTime createdAt;

}
