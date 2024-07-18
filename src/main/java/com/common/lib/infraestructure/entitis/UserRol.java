package com.common.lib.infraestructure.entitis;


import com.common.lib.infraestructure.entitis.pk.UserRolPk;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "usuarios_rol")
public class UserRol {

    @EmbeddedId
    private UserRolPk userRolPk;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", updatable = false,insertable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "id_rol", updatable = false,insertable = false)
    private Roles rol;


}
