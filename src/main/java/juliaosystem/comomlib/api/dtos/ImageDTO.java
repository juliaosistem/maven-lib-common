package juliaosystem.comomlib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.URL;

@Getter
@AllArgsConstructor
@Builder
@Data
public class ImageDTO {
    private final String url;
    public ImageDTO(byte[] data, String fileName) throws IOException {
        String urlStr = "file://" + fileName;
        URL url = new URL(urlStr);
        UrlResource resource = new UrlResource(url);
        this.url = resource.getURL().toString();
    }
}
