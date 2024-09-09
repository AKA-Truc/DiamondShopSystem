package goldiounes.com.vn.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Retype password cannot be blank")
    private String retypePassword;
}
