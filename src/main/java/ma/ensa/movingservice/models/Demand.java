package ma.ensa.movingservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.user.Client;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    private String description;

    private String sCity;

    private String dCity;

    private LocalDateTime approxTime;

    private double proposedPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "demand")
    private List<Offer> offers;

}
