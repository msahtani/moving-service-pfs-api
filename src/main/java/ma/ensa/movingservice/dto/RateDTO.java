package ma.ensa.movingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data @NoArgsConstructor @AllArgsConstructor
public class RateDTO {

    @Range(max=5)
    private int rate;
    private String comment;

}
