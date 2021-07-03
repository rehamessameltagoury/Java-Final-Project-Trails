package com.example.demo.restservice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class PopularAreaBarChartController {
    @GetMapping("/areabarchart")
    public ResponseEntity<byte[]> getImage() throws IOException {
        File img = new File("AreasChart.jpg");
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap()
                .getContentType(img)))
                .body(Files.readAllBytes(img.toPath()));
    }
}
