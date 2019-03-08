package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import static com.example.DateTimePattern.*;

public class DateTimeUtils {


    public static Date format(String date, String pattern, Locale locale) throws ParseException {
        SimpleDateFormat dateFormat;
        if (locale == null) {
            dateFormat = new SimpleDateFormat(pattern);
        } else {
            dateFormat = new SimpleDateFormat(pattern, locale);
        }
        return dateFormat.parse(date);
    }

    public static Date format(String date, String pattern) throws ParseException {
        return format(date, pattern, null);
    }

    public static Date format(String text) {
        text = text.replace("BEIST", "GMT+08:00");
        String pattern = null;
        Locale locale = null;
        Date date = null;
        try {
            pattern = PATTERN_001;
            date = DateTimeUtils.format(text, pattern);
        } catch (Exception e) {
            try {
                locale = Locale.US;
                pattern = PATTERN_002;
                date = DateTimeUtils.format(text, pattern, locale);
            } catch (Exception e1) {
                try {
                    pattern = PATTERN_003;
                    date = DateTimeUtils.format(text, pattern, locale);
                } catch (Exception e2) {
                    try {
                        locale = Locale.CHINA;
                        pattern = PATTERN_004;
                        date = DateTimeUtils.format(text, pattern, locale);
                    } catch (Exception e3) {
                        throw new DateTimeParseException(String.format("%s 按照 %s 转换异常 时区为 %s", date, pattern, locale), text, 0);
                    }
                }
            }
        }
        return date;
    }
}
