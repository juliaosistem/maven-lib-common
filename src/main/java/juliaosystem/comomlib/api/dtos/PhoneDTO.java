package juliaosystem.comomlib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PhoneDTO {
    private String number;
    private Integer cityCode;
    private Integer countryCode;
    private  String nameCity;
    private String nameCountry;

}
