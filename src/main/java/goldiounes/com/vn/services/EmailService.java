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
        String url = "http://localhost:63342/DiamondShopSystem/src/main/resources/templates/User/size_selection.html?_ijt=gasdkjuu0algrmfuoknpg60h88&_ij_reload=RELOAD_ON_SAVE";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Please select your ring size");
        message.setText("Thank you for your order! Please select your ring size using the following link: " + url);

        emailSender.send(message);
    }
}
