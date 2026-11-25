package com.common.lib.infraestructure.entitis;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Entidad que representa un permiso del sistema.
 * Los permisos son dinámicos y se almacenan como String en la BD.
 * Cada permiso puede estar asociado a múltiples roles.
 *
 * @author Twins Code
 * @version 2.0 - Refactorizado a String desde Enum
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false,onlyExplicitlyIncluded = true) // Solo usa los campos marcados
@Entity
@Table(name = "permisos", uniqueConstraints = @UniqueConstraint(
        columnNames = "nombre_permiso",
        name = "uk_permisos_nombre"
))
public class Permiso extends BaseEntities {

    @Column(name = "id_permiso")
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID idPermiso;

    @EqualsAndHashCode.Include // Clave de negocio única y estable
    @Column(name = "nombre_permiso", nullable = false, length = 100)
    private String nombrePermiso;

    @Column(length = 500)
    private String descripcion;
}

