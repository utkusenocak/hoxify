package com.hoxify.ws.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/hoax-attachments")
    public Map<String, String> saveHoaxAttachments(MultipartFile file) {
        String filename = fileService.saveHoaxAttachment(file);
        return Collections.singletonMap("name", filename);
    }
}
