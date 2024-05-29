package com.juliaosystem.api.dtos.user;


import lombok.*;


/**
 * @author daniel juliao
 * @version 1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private Integer idCity;
    private String name;
    private Integer idCountry;
}
