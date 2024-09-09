package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.ChangePasswordDTO;
import goldiounes.com.vn.models.dtos.ForgotPasswordDTO;
import goldiounes.com.vn.services.ForgotPasswordService;
import goldiounes.com.vn.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        try {
            forgotPasswordService.verifyEmailAndSendOTP(email);
            return ResponseEntity.ok("OTP sent successfully to " + email);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam Integer otp) {
        try {
            forgotPasswordService.verifyOTP(email, otp);
            return ResponseEntity.ok("OTP verified successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to verify OTP");
        }
    }

    @PostMapping("/change_password/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePasswordDTO changePasswordDTO,
                                                        @PathVariable String email) {
        try {
            if (!Objects.equals(changePasswordDTO.getPassword(), changePasswordDTO.getRetypePassword())) {
                return new ResponseEntity<>("Please enter the same password in both fields!", HttpStatus.EXPECTATION_FAILED);
            }

            userService.changePassword(email, changePasswordDTO);

            return ResponseEntity.ok("Password has been changed!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while changing the password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forgot_password/{email}")
    public ResponseEntity<String> changePasswordWhenForgot(@RequestBody ForgotPasswordDTO forgotPasswordDTO,
                                                           @PathVariable String email) {
        try {
            if (!Objects.equals(forgotPasswordDTO.getPassword(), forgotPasswordDTO.getRetypePassword())) {
                return new ResponseEntity<>("Please enter the same password in both fields!", HttpStatus.EXPECTATION_FAILED);
            }

            userService.changeForgotPassword(email, forgotPasswordDTO);
            return ResponseEntity.ok("Password has been changed!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while changing the password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
