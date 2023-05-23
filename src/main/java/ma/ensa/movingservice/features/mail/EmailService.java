package ma.ensa.movingservice.features.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService extends Thread {

    private final JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String sender;

    private static Email email;

    public static void setEmail(Email email){
        EmailService.email = email;
    }

    @Override
    public void run(){

        try{
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            System.out.println(sender);

            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getMsgBody());

            mailSender.send(mailMessage);

        }catch (Exception ex){
            ex.printStackTrace();

        }

    }

}
