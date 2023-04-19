package ma.ensa.moving.dto.auth;

import lombok.Data;
import ma.ensa.moving.enums.UserType;

@Data
public class RegisterRequest {

    private boolean isProvider;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;

    {
        isProvider = false;
    }

}