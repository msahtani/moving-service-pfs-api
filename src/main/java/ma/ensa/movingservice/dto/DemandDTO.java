package ma.ensa.movingservice.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonInclude(NON_NULL)
public class DemandDTO {
    private long id;
    private String clientName;
    private String description;
    private String when;
    private Double proposedPrice;
    private String from;
    private String to;
    private String createdAt;
}
