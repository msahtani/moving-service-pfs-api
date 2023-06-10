package ma.ensa.movingservice.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequest {


    private String fullName;
    @Email(message = "invalid email address")
    private String email;

    private String phoneNumber;
    private String password;

    private String city;
    
}