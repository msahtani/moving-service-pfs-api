package ma.ensa.movingservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.movingservice.models.Service;
import ma.ensa.movingservice.models.Vehicle;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(NON_NULL)
public class ProviderDTO {

    private long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<Vehicle> vehicles;
    private List<Service> doneServices;
    private long doneServicesCount;
}