package com.common.lib.infraestructure.entitis;

import com.common.lib.utils.enums.RolEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_rol")
    private UUID idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_rol")
    private RolEnum nameRol;

    private String  realm;

    @Column(name = "fecha_creacion")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "creador")
    private String creator;

    @OneToMany(mappedBy = "rol")
    private List<UserRol> userRols ;
    @ManyToMany
    @JoinTable(
            name = "rol_permiso",
            joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id_permiso")
    )
    private Set<Permiso> permisos;
}
