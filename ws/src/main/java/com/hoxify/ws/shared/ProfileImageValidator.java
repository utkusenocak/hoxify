package com.hoxify.ws.shared;

import com.hoxify.ws.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProfileImageValidator implements ConstraintValidator<ProfileImage, String> {
    @Autowired
    FileService fileService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        String fileType = fileService.detectType(value);
        return fileType.equalsIgnoreCase("image/jpeg") || fileType.equalsIgnoreCase("image/png");
    }
}
