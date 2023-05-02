package ma.ensa.movingservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferDTO {

    private long id;

    private long providerId;

    private String providerName;

    private Double price;

    private String description;


}
