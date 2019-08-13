package bank.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * This class allows to send email messages.
 */
@Component
public class EmailSender {

    private JavaMailSender sender;

    @Autowired
    public EmailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * This method sends an email with the given parameters.
     * @param text - text of a message.
     * @param subject - subject of a massage.
     * @param to - destination email address.
     */
    public void sendEmail(String text, String subject, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }
}
