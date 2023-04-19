package ma.ensa.moving.models.user;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.moving.enums.UserType;

import java.lang.annotation.Inherited;

@Data
@AllArgsConstructor
@SuperBuilder
@Entity
public class Client extends User {

}
