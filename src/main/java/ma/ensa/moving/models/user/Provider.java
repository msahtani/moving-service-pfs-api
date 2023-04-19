package ma.ensa.moving.models.user;


import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;
import ma.ensa.moving.enums.UserType;
import ma.ensa.moving.models.Vehicle;

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
