package juliaosystem.comomlib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterUserDTO {
    private UUID id;
    private String email;
    private String password;
    private DatesUserDTO datesUser;
    private String token;
}
