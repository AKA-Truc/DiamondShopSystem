package goldiounes.com.vn.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Retype password cannot be blank")
    private String retypePassword;
}
