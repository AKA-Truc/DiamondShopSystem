package goldiounes.com.vn.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender emailSender;

    // Method to generate a random OTP
    private String generateOTP(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public void sendSizeSelectionEmail(String to) {
        String url = "http://localhost:8080/size-selection.html";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Please select your ring size");
        message.setText("Thank you for the order. Below is a guide to measuring the size of all kinds of jewellery. After selecting the size, please return to our website: " + url + "Go to your order to re-select the size for the ordered products");
        emailSender.send(message);
    }

    public void sendOTP(String to) {
        String otp = generateOTP(6); // Generate a 6-digit OTP
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your OTP");
        message.setText("Your OTP is: " + otp);
        emailSender.send(message);
    }
}
