package com.common.lib.api.dtos;

import lombok.*;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.URL;

@Getter
@Data
public class ImageDTO {

    private final String url;

    public ImageDTO(String fileName) throws IOException {
        String urlStr = "file://" + fileName;
        URL ur = new URL(urlStr);
        UrlResource resource = new UrlResource(ur);
        this.url = resource.getURL().toString();
    }
}
