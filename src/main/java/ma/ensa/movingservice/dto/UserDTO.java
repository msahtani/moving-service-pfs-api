package ma.ensa.movingservice.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String password;
}
