package com.company;

import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    static String fileName = "access.log";
    static int numberOfLogEntries = 100;

    public static void main(String[] args) {
        writeLogs(generateFakeLogs());
        summarizeLogs(readLogs());
    }

    static List<LogEntry> generateFakeLogs(){
        String[] ips={"123.221.14.56","16.180.70.237","10.182.189.79","218.193.16.244","198.122.118.164","114.214.178.92","233.192.62.103","244.157.45.12","81.73.150.239","237.43.24.118"};
        String[] referrers={"-","http://www.casualcyclist.com","http://bestcyclingreviews.com/top_online_shops","http://bleater.com","http://searchengine.com"};
        String[] resources={"/handle-bars","/stems","/wheelsets","/forks","/seatposts","/saddles","/shifters","/Store/cart.jsp"};
        String[] userAgents={"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1944.0 Safari/537.36","Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1","Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25","Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201","Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0","Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))"};
        String[] methods={"GET", "POST"};
        String[] protocols={"HTTP/1.0", "HTTP/1.1"};
        int[] statusCodes={200, 400, 401, 403, 404, 500};

        Random random = new Random();
        LocalDateTime localDateTime = LocalDateTime.now();

        List<LogEntry> logEntries = new ArrayList<>();
        for (int i = 0; i < numberOfLogEntries; i++) {
            LogEntry logEntry = new LogEntry();
            localDateTime = localDateTime.plusSeconds(random.nextInt(300));
            logEntry.dateTime = localDateTime;
            logEntry.ip = ips[random.nextInt(ips.length)];
            logEntry.method = methods[random.nextInt(methods.length)];
            logEntry.resource = resources[random.nextInt(resources.length)];
            logEntry.protocol = protocols[random.nextInt(protocols.length)];
            logEntry.statusCode = statusCodes[random.nextInt(statusCodes.length)];
            logEntry.objectSize = random.nextInt(8000)+3000;
            logEntry.referer = referrers[random.nextInt(referrers.length)];
            logEntry.userAgent = userAgents[random.nextInt(userAgents.length)];

            logEntries.add(logEntry);
        }

        return logEntries;
    }

    static void writeLogs(List<LogEntry> logEntries){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for(LogEntry logEntry : logEntries){
                out.println(logEntry.format());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            } else {
                System.out.println("Could not open file for writing");
            }
        }
    }

    static List<LogEntry> readLogs(){
        List<LogEntry> logEntries = new ArrayList<>();

        try {

            BufferedReader buffReader = new BufferedReader(new FileReader("access.log"));

            String line;
            while((line = buffReader.readLine()) != null) {

                LogEntry logEntry = new LogEntry();
                logEntry.scan(line);
                logEntries.add(logEntry);
            }
            buffReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logEntries;
    }

    static void summarizeLogs(List<LogEntry> logEntries){

        System.out.println(checkCommonHour(logEntries));


    }

    static int checkCommonHour(List<LogEntry> logEntries){
        int[] hours = new int[24];

        for (LogEntry logEntry : logEntries) {

            LocalDateTime time = logEntry.dateTime;

            hours[time.getHour()]++;
        }

        int max = 0;
        int horaMax = -1;
        for (int j = 0; j < hours.length; j++) {
            if(hours[j] > max){
                max = hours[j];
                horaMax = j;
            }
        }

        return horaMax;
    }

    static int checkCommonWeekDay(){

    }
}
