package com.hoxify.ws.file;

import com.hoxify.ws.configuration.AppConfiguration;
import com.hoxify.ws.user.User;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService {
    AppConfiguration appConfiguration;
    Tika tika;

    FileAttachmentRepository fileAttachmentRepository;

    public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
        this.appConfiguration = appConfiguration;
        tika = new Tika();
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String filename = generateRandomName();
        File target = new File(appConfiguration.getProfileStoragepath() + "/" + filename);
        OutputStream outputStream = Files.newOutputStream(target.toPath());
        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return filename;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void deleteProfileImage(String oldImageName) {
        if (oldImageName == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getProfileStoragepath(), oldImageName));
    }

    public void deleteAttachmentFile(String name) {
        if (name == null) {
            return;
        }
        deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), name));
    }

    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String detectType(String value) {
        byte[] base64encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64encoded);
    }

    public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
        String filename = generateRandomName();
        File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + filename);
        String fileType;
        try {
            OutputStream outputStream = Files.newOutputStream(target.toPath());
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
            fileType = tika.detect(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileAttachment attachment = new FileAttachment();
        attachment.setName(filename);
        attachment.setDate(new Date());
        attachment.setFileType(fileType);
        return fileAttachmentRepository.save(attachment);
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void cleanupStorage() {
        Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        List<FileAttachment> filesToBeDeleted = fileAttachmentRepository
                .findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);
        for (FileAttachment file : filesToBeDeleted) {
            deleteAttachmentFile(file.getName());
            fileAttachmentRepository.deleteById(file.getId());
        }
    }

    public void deleteAllStoredFilesForUser(User user) {
        deleteProfileImage(user.getImage());
        List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByHoaxUser(user);
        for (FileAttachment fileAttachment : filesToBeRemoved) {
            deleteAttachmentFile(fileAttachment.getName());
        }
    }
}
