package ma.ensa.movingservice.models.user;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.movingservice.models.Vehicle;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class Provider extends User {

    @OneToMany(mappedBy = "provider")
    private Set<Vehicle> vehicles;

    @ManyToOne
    private Admin acceptedBy;

    private String city;

}
