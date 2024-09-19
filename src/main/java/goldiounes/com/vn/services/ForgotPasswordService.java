package goldiounes.com.vn.services;

import goldiounes.com.vn.models.entities.User;
import goldiounes.com.vn.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService{
    private final UserRepo userRepository;

    @Autowired
    private JavaMailSender emailSender;

    // Sử dụng ConcurrentHashMap để lưu trữ OTP
    private final Map<String, OTPData> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void verifyEmailAndSendOTP(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        String otp = otpGenerator(6);

        OTPData otpData = new OTPData(otp, new Date(System.currentTimeMillis() + 60 * 1000));
        otpStorage.put(email, otpData);

        System.out.println("Đã lưu OTP: " + otp + " cho email: " + email);

        sendOtpMail(user.getEmail(), otp);

        executorService.schedule(() -> otpStorage.remove(email), 60, TimeUnit.SECONDS);
    }


    private String otpGenerator(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public void verifyOTP(String email, Integer otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
        }

        OTPData otpData = otpStorage.get(email);
        if (otpData != null) {
            System.out.println("OTP từ otpStorage: " + otpData.getOtp() + " cho email: " + email);

            if (otpData.getOtp().equals(otp.toString())) {
                if (otpData.getExpirationTime().before(new Date())) {
                    throw new RuntimeException("Mã OTP đã hết hạn!");
                }

                otpStorage.remove(email); //Xóa OTP sau khi xác minh thành công
                System.out.println("Xác minh OTP thành công cho email: " + email);
            } else {
                throw new RuntimeException("Mã OTP không hợp lệ cho email: " + email);
            }
        } else {
            throw new RuntimeException("Không tìm thấy OTP hợp lệ cho email: " + email);
        }
    }


    private void sendOtpMail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your OTP");
        message.setText("Your OTP is: " + otp);
        emailSender.send(message);
    }

    // Lớp nội bộ để lưu trữ OTP và thời gian hết hạn
    private static class OTPData {
        private final String otp;
        private final Date expirationTime;

        public OTPData(String otp, Date expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }

        public String getOtp() {
            return otp;
        }

        public Date getExpirationTime() {
            return expirationTime;
        }
    }
}
