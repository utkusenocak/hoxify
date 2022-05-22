package com.hoxify.ws.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "hoxify")
public class AppConfiguration {
    private String uploadPath;
    private String profileStorage = "profile";
    private String attachmentStorage = "attachments";

    public String getProfileStoragepath() {
        return uploadPath + "/" + profileStorage;
    }

    public String getAttachmentStoragePath() {
        return uploadPath + "/" + attachmentStorage;
    }
}
