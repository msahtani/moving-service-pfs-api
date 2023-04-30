package ma.ensa.movingservice.features.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {
    private String recipient;
    private String subject;
    private String msgBody;
}
