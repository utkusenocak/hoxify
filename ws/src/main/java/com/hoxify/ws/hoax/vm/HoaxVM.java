package com.hoxify.ws.hoax.vm;

import com.hoxify.ws.hoax.Hoax;
import com.hoxify.ws.user.vm.UserVM;
import lombok.Data;

@Data
public class HoaxVM {
    private Long id;
    private String content;
    private long timestamp;
    private UserVM userVM;
    public HoaxVM(Hoax hoax) {
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setTimestamp(hoax.getTimestamp().getTime());
        this.setUserVM(new UserVM(hoax.getUser()));
    }
}
