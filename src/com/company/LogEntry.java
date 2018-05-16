package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");

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

    }
}
