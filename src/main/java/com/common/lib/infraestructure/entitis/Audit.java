package com.common.lib.infraestructure.entitis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "audit")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audit {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String ip;
    private String dominio;
    private String usuario;
    private Long idBussines;
    private String proceso;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @Lob
    private String logs;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return "Audit{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", dominio='" + dominio + '\'' +
                ", usuario='" + usuario + '\'' +
                ", idBussines=" + idBussines +
                ", proceso='" + proceso + '\'' +
                ", timestamp=" + timestamp.format(formatter) +
                ", logs='" + logs + '\'' +
                '}';
    }
}
