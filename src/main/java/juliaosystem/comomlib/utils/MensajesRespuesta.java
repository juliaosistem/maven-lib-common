package juliaosystem.comomlib.utils;

import lombok.*;


@AllArgsConstructor
@Data
@Builder
@Getter
@NoArgsConstructor
public class MensajesRespuesta {

    public String creado = "creado(a) Correactamente";
    public String noEncontrado= "No se Encontraron Datos";

    public String get = "Se obtuvieron datos correctamente";
    public  String Actualizado = "Actualizado Correctamente";

    public String Eliminado = "Eliminado Correctamente";

    private String mensaje_personalizado ;

    public String fallo = "Algo salio mal ";

    public static String crearMensaje(String mensaje){
        return  mensaje;
    }

}
