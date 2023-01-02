package com.project.bumawiki.domain.image;

import com.project.bumawiki.domain.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;




@RestController
@RequestMapping("/image")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public void FileController(ImageService imageService){
        this.imageService = imageService;

    }
    @GetMapping("/display/{DocsName}/{fileName}")
    public ResponseEntity<Resource> displayImage(@PathVariable String fileName,
                                                 @PathVariable String DocsName,
                                                 HttpServletRequest request) {
        // Load file as Resource

        Resource resource = imageService.loadFileAsResource(DocsName, fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            System.out.println(contentType);
        } catch (IOException ex) {
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Type",contentType)
                .body(resource);
    }

}
