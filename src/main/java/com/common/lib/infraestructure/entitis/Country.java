package com.common.lib.infraestructure.entitis;


import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "paises")
public class Country {
    @Column(name = "id_Pais")
    @Id
    @NotNull
    private Integer idCountry;

    @Column(name = "nombre")
    private String name;

    @Column(name = "codigo_iso")
    private String codigoIso;

    private String url;

    @OneToMany(mappedBy = "country")
    private List<City> cities;

    @Override
    public String toString() {
        return "Country{" +
                "idCountry=" + idCountry +
                ", name='" + name + '\'' +
                ", codigoIso='" + codigoIso + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
