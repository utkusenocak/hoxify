package com.hoxify.ws.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {
    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String filename = generateRandomName();
        File target = new File("pictures/"+filename);
        OutputStream outputStream = Files.newOutputStream(target.toPath());
        byte[] base64encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64encoded);
        outputStream.close();
        return filename;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
