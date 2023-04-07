package fa.training.models.dtos;

import fa.training.models.entities.Member;
import fa.training.utils.MessError;
import fa.training.utils.Regex;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * A DTO for the {@link Member} entity
 */
@Data

public class UserDTO implements Serializable {

    @NotBlank(message = MessError.VALID_USERNAME_BLANK_MESS)
    private String userName;

    @Pattern(regexp = Regex.VALID_EMAIL_REGEX, message = MessError.VALID_EMAIL_ERROR_MESS)
    @NotBlank(message = MessError.VALID_EMAIL_BLANK_MESS)
    private String email;

    @NotBlank(message = MessError.VALID_PASSWORD_BLANK_MESS)
    private String password;
}