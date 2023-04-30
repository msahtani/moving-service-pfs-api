package ma.ensa.movingservice.dto.auth;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;


}
