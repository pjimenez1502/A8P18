package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");
//    static Pattern pattern = Pattern.compile("(\\d*\\.\\d*\\.\\d*\\.\\d*)\\s-\\s-\\s\\[(\\d/\\d/\\d:\\d:\\d:\\d)]\\s\"(\\A)\\s(.*)\\s(.*)\"\\s(\\d*)\\s(\\d*)\\s\"(.*)\"\\s\"(.*)");
    static Pattern pattern = Pattern.compile("(.*)\\s-\\s-\\s\\[(.*)\\]\\s\"(.*)\\s(.*)\\s(.*)\"\\s(\\d*)\\s(\\d*)\\s\"(.*)\"\\s\"(.*)");
    String ip;
    LocalDateTime dateTime;
    String method;
    String resource;
    String protocol;
    int statusCode;
    int objectSize;
    String referer;
    String userAgent;

    String format(){
        return String.format("%s - - [%s] \"%s %s %s\" %s %s \"%s\" \"%s\"", ip, dateTime.format(LogEntry.dateTimeFormatter), method, resource, protocol, statusCode, objectSize, referer, userAgent);
    }

    void scan(String line){
        Matcher matcher = pattern.matcher(line);
        if(matcher.matches()){

            String date = matcher.group(2);
            String[] finaldate = date.split("[/:]");

            ip = matcher.group(1);
            dateTime = LocalDateTime.of(Integer.parseInt(finaldate[2]),Integer.parseInt(finaldate[1]),Integer.parseInt(finaldate[0]),Integer.parseInt(finaldate[3]),Integer.parseInt(finaldate[4]),Integer.parseInt(finaldate[5]));
            method = matcher.group(3);
            resource = matcher.group(4);
            protocol = matcher.group(5);
            statusCode = Integer.parseInt(matcher.group(6));
            objectSize = Integer.parseInt(matcher.group(7));
            referer = matcher.group(8);
            userAgent = matcher.group(9);
        }
    }


}
