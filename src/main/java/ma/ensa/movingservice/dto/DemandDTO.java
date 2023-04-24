package ma.ensa.movingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandDTO {

    private String clientName;
    private String title;
    private String description;
    private LocalDateTime when;
    private Double proposedPrice;
    private String from;
    private String to;
}
