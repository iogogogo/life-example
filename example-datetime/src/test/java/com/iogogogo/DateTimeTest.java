package com.iogogogo;

import com.iogogogo.util.DateTimePattern;
import com.iogogogo.util.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateTimeTest {

    @Test
    public void test1() {
        String date = "1996-05-18 23:20:50.000000000 +0800";
        try {
            Date format = DateTimeUtils.format(date, DateTimePattern.PATTERN_001);
            log.info("time:{}", format);
            date = "1995-1-06 23:20:50.000000000 +0800";
            format = DateTimeUtils.format(date, DateTimePattern.PATTERN_001);
            log.info("time:{}", format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String date = "Thu Mar 07 15:42:43 2019";
        try {
            Date format = DateTimeUtils.format(date, DateTimePattern.PATTERN_002, Locale.US);
            log.info("time:{}", format);
            date = "Fri Nov 2 12:38:03 2018";
            format = DateTimeUtils.format(date, DateTimePattern.PATTERN_002, Locale.US);
            log.info("time:{}", format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        String date = "Fri Apr 10 00:00:00 GMT+08:00 2015";
        try {
            Date format = DateTimeUtils.format(date, DateTimePattern.PATTERN_003, Locale.US);
            log.info("time:{}", format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        String date = "Fri Apr 03 00:00:00 CST 2015";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
    }

    @Test
    public void test5() {
        String date = "Fri Apr 10 00:00:00 BEIST 2015";
        date = date.replace("BEIST", "GMT+08:00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
    }

    @Test
    public void test6() {
        String date = "公元2018年03月28日 星期三 18时17分55秒";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("公元yyyy年MM月dd日 E HH时mm分ss秒", Locale.CHINA);
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
    }

    @Test
    public void test7() {
        String date = "公元2019年03月7日 星期四 18时17分55秒";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("公元yyyy年MM月d日 E HH时mm分ss秒", Locale.CHINA);
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
    }


    @Test
    public void test8() {
        LocalDateTime now = LocalDateTime.now();

        log.info("now:{}", now);

        log.info("{}", new Date());
    }

    @Test
    public void test() {
        String date = "1996-05-18 23:20:50.000000000 +0800";
        Date dateTime = DateTimeUtils.format(date);

        log.info("dateTime:{}", dateTime);

        date = "Fri Apr 10 00:00:00 2015";
        dateTime = DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "Fri Apr 10 00:00:00 GMT+08:00 2015";
        dateTime = DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "Fri Apr 10 00:00:00 CST 2015";
        dateTime = DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "Fri Apr 10 00:00:00 BEIST 2015";
        dateTime = DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "公元2018年03月28日 星期三 18时17分55秒";
        dateTime = DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "2017-01-01 12:00:00.000000001 +0800";
        dateTime = DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);

        dateTime = DateTimeUtils.format(new Date().toString());
        log.info("dateTime:{}", dateTime);


        dateTime = DateTimeUtils.format("Thu Mar 7 16:15:59 CST 2019");
        log.info("dateTime:{}", dateTime);


    }
}
