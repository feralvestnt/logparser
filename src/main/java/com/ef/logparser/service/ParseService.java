package com.ef.logparser.service;

import com.ef.logparser.comum.ParamsReader;
import com.ef.logparser.model.Interval;

import java.time.LocalDateTime;

public class ParseService {

    public static void parse(String[] args) {

        ParamsReader paramsReader = new ParamsReader();
        paramsReader.get(args);

        Integer threshold = paramsReader.getIntParam("threshold");
        String duration = paramsReader.getStringParam("duration");

        LocalDateTime startDate = paramsReader.getLocalDateTimeParam("startDate");
        Interval interval = Interval.chooseFormat(startDate, duration);

        FileService fileService = new FileService();

        fileService.proccess(interval, threshold);
    }
}
