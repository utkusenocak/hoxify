package com.hoxify.ws.hoax.vm;

import com.hoxify.ws.hoax.Hoax;
import lombok.Data;

import java.util.Date;

@Data
public class HoaxVM {
    private String content;
    private Date timestamp;
    public HoaxVM(Hoax hoax) {
        this.setContent(hoax.getContent());
        this.setTimestamp(hoax.getTimestamp());
    }
}
