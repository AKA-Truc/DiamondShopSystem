package goldiounes.com.vn.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender emailSender;

    public void sendSizeSelectionEmail(String to) {
        String url = "http://localhost:8080/size-selection.html";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Please select your ring size");
        message.setText("Thank you for the order. Below is a guide to measuring the size of all kinds of jewellery. After selecting the size, please return to our website: " + url + "Go to your order to re-select the size for the ordered products");
        emailSender.send(message);
    }
}
