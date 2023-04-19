package ma.ensa.moving.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.moving.models.user.Provider;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Offer {

    // TODO: id ....
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

    private double price;

    private boolean accepted;

}
