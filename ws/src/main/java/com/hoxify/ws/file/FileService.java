package com.hoxify.ws.file;

import com.hoxify.ws.configuration.AppConfiguration;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {
    AppConfiguration appConfiguration;
    Tika tika;

    public FileService(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
        tika = new Tika();
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String filename = generateRandomName();
        File target = new File(appConfiguration.getUploadPath() +"/"+filename);
        OutputStream outputStream = Files.newOutputStream(target.toPath());
        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return filename;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void deleteFile(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String detectType(String value) {
        byte[] base64encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64encoded);
    }

    public String saveHoaxAttachment(MultipartFile multipartFile) {
        String filename = generateRandomName();
        File target = new File(appConfiguration.getUploadPath() +"/"+filename);
        try {
            OutputStream outputStream = Files.newOutputStream(target.toPath());
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return filename;
    }
}
