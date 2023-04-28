package ma.ensa.movingservice.dto.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    // TODO: delete 'provider' flag
    private boolean provider;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;

    private String city;
    
}