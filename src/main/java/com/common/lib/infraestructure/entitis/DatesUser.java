package com.common.lib.infraestructure.entitis;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "datos_usuarios")
public class DatesUser {
    @Column(name = "id_datos_usuario")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID idDatesUser;

    @Column(name = "primer_nombre")
    private String firstName;

    private String idUrl;

    @Column(name = "segundo_nombre")
    private String secondName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "datesUser")
    private List<Phone> phone;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "datesUser")
    private List<Address> addresses;

    @OneToOne
    @JoinColumn(name = "id_usuario", updatable = false, insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estates state;


    @Override
    public String toString() {
        return "DatesUser{" +
                "idDatesUser=" + idDatesUser +
                ", firstName='" + firstName + '\'' +
                ", idUrl='" + idUrl + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phone=" + phone +
                ", addresses=" + addresses +
                ", user=" + (user != null ? user.getId_usuario() : null) +
                ", state=" + (state != null ? state.getIdEstate() : null) +
                '}';
    }


}
