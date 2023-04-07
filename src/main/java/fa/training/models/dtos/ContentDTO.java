package fa.training.models.dtos;

import fa.training.models.entities.Content;
import fa.training.utils.MessError;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Content} entity
 */
@Data
public class ContentDTO implements Serializable {
    @NotBlank(message = MessError.VALID_TITLE_BLANK_MESS)
    private String title;
    @NotBlank(message = MessError.VALID_BRIEF_BLANK_MESS)
    private String brief;
    @NotBlank(message = MessError.VALID_CONTENT_BLANK_MESS)
    private String content;
}