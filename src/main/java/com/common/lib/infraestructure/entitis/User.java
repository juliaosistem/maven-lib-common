package com.common.lib.infraestructure.entitis;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@AttributeOverride(name = "idBusiness", column = @Column(name = "id_business", nullable = false))
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "uk_users_email_tenant", columnNames = {"email", "id_business"}),
    @UniqueConstraint(name = "uk_users_keycloak_tenant", columnNames = {"keycloak_id", "id_business"}),
    @UniqueConstraint(
    name = "uk_users_social_identity",
    columnNames = {"social_provider", "social_subject"})})
public class User  extends BaseEntities{

    @Id
    @GeneratedValue
    @Column(name = "id_usuario")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id_usuario;


    @Column(name = "saldo")
    private Double balance;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Column(nullable = false)
    private String email;

    @Column(name = "keycloak_id")
    private UUID keycloakId;

    @Column(name = "social_provider", length = 40)
    private String socialProvider;

    @Column(name = "social_subject", length = 255)
    private String socialSubject;

    private LocalDateTime lastLogin;

    @OneToOne(mappedBy = "user")
    private DatesUser datesUser;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRol> userRols;




}
