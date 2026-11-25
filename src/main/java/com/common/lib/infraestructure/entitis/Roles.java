package com.common.lib.infraestructure.entitis;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Entidad que representa un rol del sistema.
 * Los roles son dinámicos y se almacenan como String en la BD.
 * Cada rol puede tener múltiples permisos asociados.
 *
 * @author Twins Code
 * @version 2.0 - Refactorizado a Strings desde Enums
 */

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(
    name = "roles",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"nombre_rol", "id_business"},
        name = "uk_roles_nombre_id_business"
    )
)
public class Roles extends BaseEntities {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id_rol")
    private UUID idRol;

    @Column(name = "nombre_rol", nullable = false, length = 100)
    @Description("Nombre del rol como String dinámico (ADMIN, USER, COSTURERO, etc.)")
    private String nameRol;

    @Column(name = "id_business", nullable = false)
    @Description("Identificador del negocio/empresa para multi-tenancy")
    private Integer idBusiness;

    @Column(length = 500)
    @Description("Descripción del rol y sus responsabilidades")
    private String descripcion;

    @Column(name = "realm")
    @Description("Realm de Keycloak asociado a este rol")
    private String realm;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<UserRol> userRols;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
        name = "rol_permiso",
        joinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id_rol"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id_permiso")
    )
    private Set<Permiso> permisos;

}
