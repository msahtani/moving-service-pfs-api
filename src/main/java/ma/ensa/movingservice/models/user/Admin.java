package ma.ensa.movingservice.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@SuperBuilder

@Entity
public class Admin extends User {

    @JsonIgnore
    public boolean notSudo(){
        return id != 1L;
    }

}