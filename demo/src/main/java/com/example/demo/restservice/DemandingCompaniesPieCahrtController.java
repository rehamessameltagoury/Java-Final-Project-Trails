package com.example.demo.restservice;

import org.springframework.util.StreamUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DemandingCompaniesPieCahrtController {

    @GetMapping("/companypiechart")
    public ResponseEntity<byte[]> getImage() throws IOException {
        File img = new File("PieChart.jpg");
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap()
                .getContentType(img)))
                .body(Files.readAllBytes(img.toPath()));
    }
}







