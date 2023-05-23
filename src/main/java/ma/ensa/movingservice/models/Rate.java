package ma.ensa.movingservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Rate {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(mappedBy = "rate")
    private Service service;

    private int rate;

    private String comment;

}
