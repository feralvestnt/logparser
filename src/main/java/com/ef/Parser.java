package com.ef;

import com.ef.logparser.service.ParseService;

public class Parser {

    public static void main(String[] args) {
        try {
            ParseService.parse(args);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
