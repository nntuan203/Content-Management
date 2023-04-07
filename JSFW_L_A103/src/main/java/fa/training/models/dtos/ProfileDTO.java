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
public class ProfileDTO implements Serializable {
    @NotBlank(message = MessError.VALID_FIRSTNAME_BLANK_MESS)
    private String firstName;
    @NotBlank(message = MessError.VALID_LASTNAME_BLANK_MESS)
    private String lastName;
    @NotBlank(message = MessError.VALID_PHONE_BLANK_MESS)
    private String phone;
    @Pattern(regexp = Regex.VALID_EMAIL_REGEX, message = MessError.VALID_EMAIL_ERROR_MESS)
    @NotBlank(message = MessError.VALID_EMAIL_BLANK_MESS)
    private String email;
    private String description;
}