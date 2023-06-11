package ma.ensa.movingservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeclamationDto {

    private long id;

    // for response
    private String declaimedName, declaimerName;

    private long declaimedId;

    private String description;

    private String createdAt;


}
