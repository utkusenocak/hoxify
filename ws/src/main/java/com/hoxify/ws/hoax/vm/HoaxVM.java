package com.hoxify.ws.hoax.vm;

import com.hoxify.ws.hoax.Hoax;
import com.hoxify.ws.user.vm.UserVM;
import lombok.Data;

import java.util.Date;

@Data
public class HoaxVM {
    private String content;
    private long timestamp;
    private UserVM userVM;
    public HoaxVM(Hoax hoax) {
        this.setContent(hoax.getContent());
        this.setTimestamp(hoax.getTimestamp().getTime());
        this.setUserVM(new UserVM(hoax.getUser()));
    }
}
