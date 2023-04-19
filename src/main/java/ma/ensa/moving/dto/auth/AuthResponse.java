package ma.ensa.moving.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.ensa.moving.enums.UserType;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private String userType;
}
