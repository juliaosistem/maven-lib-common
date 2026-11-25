package com.common.lib.infraestructure.entitis;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "datos_usuarios")
public class DatesUser extends BaseEntities {
    @Column(name = "id_datos_usuario")
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID idDatesUser;

    @Column(name = "primer_nombre")
    private String firstName;

    private String idUrl;

    @Column(name = "segundo_nombre")
    private String secondName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "datesUser")
    private List<Phone> phone;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "datesUser")
    @BatchSize(size = 25)
    private List<Address> addresses;

    @OneToOne
    @JoinColumn(name = "id_usuario", updatable = false, insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estados state;

}
