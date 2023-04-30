package ma.ensa.movingservice.features.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    public boolean sendEmail(Email email){


        try{
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getMsgBody());

            mailSender.send(mailMessage);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

}
