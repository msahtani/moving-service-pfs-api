package ma.ensa.movingservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VehicleDTO {

    @NotNull(message = "imm number must be not null")
    private String imm;

    private String brand;

    private String model;

    private MultipartFile image;

}
