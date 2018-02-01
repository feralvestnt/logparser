package com.ef.logparser.service;

import com.ef.logparser.dao.LogDao;
import com.ef.logparser.model.BlockedIp;
import com.ef.logparser.model.Interval;
import com.ef.logparser.model.Log;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileService {

    public void proccess( Interval interval, Integer threshold) {

        File logFile = new File("e:\\access.log");

        List<Log> listLog = loadFileData(logFile);

        Map<String, Long> stringLongMap = countRequests(listLog, interval, threshold);

        Set<BlockedIp> blockedIps = getBlockeds(stringLongMap);

        saveLogs(listLog, blockedIps);
    }

    private List<Log> loadFileData(File logFile) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)))) {
            return br.lines()
                    .map(line -> convertFileLinesToLog(line))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.println("ERROR READING FILE... ");
        }
        return new ArrayList<>();
    }

    private Map<String, Long> countRequests(List<Log> listLog, Interval interval, Integer threshold) {
        return listLog.stream()
                .filter(entry -> interval.between(entry.getDate()))
                .map(entry -> entry.getIp())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() >= threshold)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    private Set<BlockedIp> getBlockeds(Map<String, Long> stringLongMap) {
        return stringLongMap.entrySet().stream().map(entry -> {
            BlockedIp blocked = BlockedIp.of(
                    entry.getKey(),
                    entry.getValue());
            System.out.println(blocked.toString());
            return blocked;
        }).collect(Collectors.toSet());
    }

    private void saveLogs(List<Log> listLog, Set<BlockedIp> blockedIps) {
        LogDao.getInstance().save(listLog);
        LogDao.getInstance().save(blockedIps);
        LogDao.getInstance().close();
    }

    private Log convertFileLinesToLog(String fileLine) {

        String DELIMITER = "\\|";

        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        String IP_FORMAT = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

        try {
            String[] parts = fileLine.split(DELIMITER);

            LocalDateTime date = LocalDateTime.parse(parts[0], DATE_FORMAT);

            if(!Pattern.matches(IP_FORMAT, parts[1])) {
                throw new IllegalArgumentException("Parse error in IP value");
            }

            if(parts.length != 5) {
                throw new IllegalArgumentException("Error in Log format: " + fileLine);
            }

            String ip = parts[1];
            String request = parts[2];
            int responseCode = Integer.parseInt(parts[3]);

            String userInfo = parts[4];
            return new Log(date, ip, request, responseCode, userInfo);
        } catch(DateTimeParseException ex ) {
            throw new IllegalArgumentException("An error ocurred parsing date", ex);
        } catch(NumberFormatException ex) {
            throw new IllegalArgumentException("An error ocurred parsing response", ex);
        } catch(Exception ex) {
            throw new IllegalArgumentException("An error ocurred parsing log line: " + fileLine, ex);
        }
    }
}
