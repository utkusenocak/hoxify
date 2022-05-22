package com.hoxify.ws.file.vm;

import com.hoxify.ws.file.FileAttachment;
import lombok.Data;

@Data
public class FileAttachmentVM {
    private String name;

    public FileAttachmentVM(FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
    }
}

