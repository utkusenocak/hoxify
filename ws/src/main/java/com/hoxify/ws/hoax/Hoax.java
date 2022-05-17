package com.hoxify.ws.hoax;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Hoax {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
}
