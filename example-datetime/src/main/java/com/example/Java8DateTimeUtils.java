package com.example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import static com.example.DateTimePattern.*;

public class Java8DateTimeUtils {

    public static String parse(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String parse(LocalDateTime localDateTime) {
        return parse(localDateTime, PATTERN_YYYY_MM_DD);
    }


    public static LocalDateTime format(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    public static LocalDateTime format(String date, String pattern, Locale locale) throws DateTimeParseException {
        DateTimeFormatter formatter;
        if (locale == null) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        } else {
            formatter = DateTimeFormatter.ofPattern(pattern, locale);
        }
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDateTime format(String date) {
        // https://www.cnblogs.com/wangziyi0513/p/10106157.html
        // BEIST 格式转换成 GMT+08:00
        date = date.replace("BEIST", "GMT+08:00");
        LocalDateTime localDateTime;
        String pattern = null;
        Locale locale = null;
        try {
            pattern = PATTERN_001;
            localDateTime = Java8DateTimeUtils.format(date, pattern, null);
        } catch (Exception e) {
            try {
                locale = Locale.US;
                pattern = PATTERN_002;
                localDateTime = Java8DateTimeUtils.format(date, pattern, locale);
            } catch (Exception e1) {
                try {
                    pattern = PATTERN_003;
                    localDateTime = Java8DateTimeUtils.format(date, pattern, locale);
                } catch (Exception e2) {
                    try {
                        locale = Locale.CHINA;
                        pattern = PATTERN_004;
                        localDateTime = Java8DateTimeUtils.format(date, pattern, locale);
                    } catch (Exception e3) {
                        throw new DateTimeParseException(String.format("%s 按照 %s 转换异常 时区为 %s", date, pattern, locale), date, 0);
                    }
                }
            }
        }

        return localDateTime;
    }
}
