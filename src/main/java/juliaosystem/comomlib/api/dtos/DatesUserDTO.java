package juliaosystem.comomlib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * @author daniel juliao
 * @version 1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatesUserDTO {

    private UUID idDatesUser;
    private String firstName;
    private String secondName;
    private List<PhoneDTO> phone;
    private String idUrl;
    private List<AddresDTO> addresses;
    private UUID estado;
    private String nombreRol;

}
