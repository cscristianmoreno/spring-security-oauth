package com.example.myapp.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class DateUtil {
    
    public static long addCurrentDateSeconds(int seconds) {
        LocalDateTime localDateTime = LocalDateTime.now();
        long expire = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return expire + (seconds * 1000);
    } 

    public static LocalDateTime currentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime;
    }
}
