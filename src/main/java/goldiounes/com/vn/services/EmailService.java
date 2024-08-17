package goldiounes.com.vn.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSizeSelectionEmail(String to, int orderId) {
        String url = "http://localhost:8080/size-selection?orderId=" + orderId;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Please select your ring size");
        message.setText("Thank you for your order! Please select your ring size using the following link: " + url);

        emailSender.send(message);
    }
}
