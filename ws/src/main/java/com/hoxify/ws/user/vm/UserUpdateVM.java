package com.hoxify.ws.user.vm;

import com.hoxify.ws.shared.FileType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateVM {
    @NotNull
    @Size(min = 4, max = 255)
    private String displayName;
    @FileType(types = {"jpeg", "png"})
    private String image;
}
