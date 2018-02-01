package com.ef.logparser.comum;

import com.ef.logparser.dto.KeyValueParam;
import lombok.Value;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsReader {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    private Map<String, String> paramsValues;

    public void get(String[] args) {
        this.paramsValues = getParamsValues(args);
    }

    private static Map<String, String> getParamsValues(String[] args) {
        List<KeyValueParam> keyValueParamList =
                Arrays.stream(args).map(arg -> {
            String[] keyAndValue = arg.split("=");
            String key = keyAndValue[0].replaceAll("\\-\\-", "").trim();
            String value = keyAndValue[1].trim();
            return new KeyValueParam(key, value);
        }).collect(Collectors.toList());

        return keyValueParamList
                .stream()
                .collect(Collectors.toMap(keyValue -> keyValue.getKey(), keyValue -> keyValue.getValue()));
    }

    public String getStringParam(String param) {
        checkIfParamExists(param);
        return paramsValues.get(param);
    }

    public int getIntParam(String param) {
        checkIfParamExists(param);
        try {
            return Integer.parseInt(paramsValues.get(param));
        } catch(NumberFormatException ex) {
            throw new IllegalArgumentException("Could not parse " + param + ": "
                    + paramsValues.get(param), ex);
        }
    }

    public LocalDateTime getLocalDateTimeParam(String param) {
        checkIfParamExists(param);
        try {
            return LocalDateTime.parse(paramsValues.get(param), DATE_FORMAT);
        } catch(DateTimeException ex) {
            throw new IllegalArgumentException("Could not parse " + param + ": "
                    + paramsValues.get(param), ex);
        }
    }

    private void checkIfParamExists(String param) {
        if(!exists(param)) {
            throw new IllegalArgumentException("Param does not exists " + param);
        }
    }

    public boolean exists(String argName) {
        return paramsValues.containsKey(argName);
    }
}
