package com.ef.logparser.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blocked_ip")
public class BlockedIp {

    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ip;

    @Column(name = "total_requests")
    private Long totalRequests;

    private String reason;

    @Override
    public String toString() {
        return reason;
    }


    public static BlockedIp of(String ip, Long totalRequests) {
        String reason = "Too many requests for que period especified.";
        return new BlockedIp(ip, totalRequests, reason);
    }

    private BlockedIp(String ip, Long totalRequests, String reason) {
        this.ip = ip;
        this.reason = reason;
        this.totalRequests = totalRequests;

    }

    private BlockedIp() {
        // Required by Hibernate
    }
}
