package juliaosystem.comomlib.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlantillaResponse<E>{
    private boolean rta;
    private String message;
    private HttpStatus httpStatus;
    private E data;
    private List<E> dataList;
}
