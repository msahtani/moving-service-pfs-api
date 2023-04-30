package ma.ensa.movingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.Vehicle;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProviderDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<Vehicle> vehicles;
    private List<Service> doneServices;
}