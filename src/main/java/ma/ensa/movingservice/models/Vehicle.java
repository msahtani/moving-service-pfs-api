package ma.ensa.movingservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.user.Admin;
import ma.ensa.movingservice.models.user.Provider;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Vehicle {

    @Id
    private String imm;

    private String brand;

    private String model;

    @ManyToOne
    private Provider provider;

    private String imageName;

    @ManyToOne
    private Admin verifiedBy;
}
