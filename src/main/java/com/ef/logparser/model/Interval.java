package com.ef.logparser.model;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class Interval {

    @Getter
    private final LocalDateTime initialDate;

    @Getter
    private final LocalDateTime endDate;

    public static Interval daily(LocalDateTime initialDate) {
        return new Interval(initialDate, initialDate.plusDays(1));
    }

    public static Interval hourly(LocalDateTime initialDate) {
        return new Interval(initialDate, initialDate.plusHours(1));
    }

    public static Interval chooseFormat(LocalDateTime initialDate, String format) {
        if (format.equals("daily")) {
            return Interval.daily(initialDate);
        } else if (format.equals("hourly")) {
            return Interval.hourly(initialDate);
        } else {
            throw new IllegalArgumentException("'" + format + "' not allowed. ");
        }
    }

    public Interval(LocalDateTime initialDate, LocalDateTime endDate) {
        this.initialDate = initialDate;
        this.endDate = endDate;
    }

    public boolean between(LocalDateTime date) {
        return date.isAfter(initialDate) && date.isBefore(endDate);
    }
}
