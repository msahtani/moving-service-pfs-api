package ma.ensa.movingservice.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
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
    @GeneratedValue()
    private Long id;

    @Column(unique = true)
    private String imm;

    private String brand;

    private String model;

    @ManyToOne @JsonIgnore
    private Provider provider;

    private String imageName;

    @ManyToOne @JsonIgnore
    private Admin verifiedBy;

    public long providerId(){
        return provider.getId();
    }

    public boolean accepted(){
        return verifiedBy != null;
    }

}
