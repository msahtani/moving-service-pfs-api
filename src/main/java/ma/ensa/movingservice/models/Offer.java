package ma.ensa.movingservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.user.Provider;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Offer {

    @Id
    @SequenceGenerator(
            name = "offer_sequence",
            sequenceName = "offer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "offer_sequence"
    )
    private long id;

    @ManyToOne
    private Provider provider;

    @ManyToOne
    private Demand demand;

    @OneToOne
    private Service service;

    private String description;

    private double price;

}
