package com.ef.logparser.model;

import com.ef.logparser.comum.DateTimeConverter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "LOG")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer id;

    private String ip;

    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime date;

    private String request;

    @Column(name = "response_code")
    private int responseCode;

    @Column(name = "user_info")
    private String userInfo;

    public Log() {}

    public Log(LocalDateTime date, String ip, String request, int responseCode, String userInfo) {
        this.date = date;
        this.ip = ip;
        this.request = request;
        this.responseCode = responseCode;
        this.userInfo = userInfo;
    }
}
