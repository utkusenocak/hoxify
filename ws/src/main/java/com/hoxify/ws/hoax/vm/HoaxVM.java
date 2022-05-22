package com.hoxify.ws.hoax.vm;

import com.hoxify.ws.file.vm.FileAttachmentVM;
import com.hoxify.ws.hoax.Hoax;
import com.hoxify.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class HoaxVM {
    private Long id;
    private String content;
    private long timestamp;
    private UserVM user;
    private FileAttachmentVM fileAttachment;

    public HoaxVM(Hoax hoax) {
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setTimestamp(hoax.getTimestamp().getTime());
        this.setUser(new UserVM(hoax.getUser()));
        if (hoax.getFileAttachment() != null) {
            this.setFileAttachment(new FileAttachmentVM(hoax.getFileAttachment()));
        }
    }
}
