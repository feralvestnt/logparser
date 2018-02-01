package com.ef.logparser.dto;

import lombok.Data;

@Data
public class KeyValueParam {
    public String key;
    public String value;

    public KeyValueParam(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
