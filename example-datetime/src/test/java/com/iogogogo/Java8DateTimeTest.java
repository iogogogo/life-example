package com.iogogogo;

import com.iogogogo.util.DateTimePattern;
import com.iogogogo.util.Java8DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Java8DateTimeTest {

    @Test
    public void test1() {
        String date = "1996-05-18 23:20:50.000000000 +0800";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS Z");
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);

        date = "1995-1-06 23:20:50.000000000 +0800";
        formatter = DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss.SSSSSSSSS Z");
        time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
    }

    @Test
    public void test2() {
        String date = "Thu Mar 07 15:42:43 2019";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss yyyy", Locale.US);

        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);


        date = "Fri Nov 2 12:38:03 2018";

        formatter = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss yyyy", Locale.US);

        time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
    }

    @Test
    public void test3() {
        String date = "Fri Apr 10 00:00:00 GMT+08:00 2015";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        log.info("time:{}", time);
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
        LocalDateTime dateTime = Java8DateTimeUtils.format(date);

        log.info("dateTime:{}", dateTime);

        date = "Fri Apr 10 00:00:00 2015";
        dateTime = Java8DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "Fri Apr 10 00:00:00 GMT+08:00 2015";
        dateTime = Java8DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "Fri Apr 10 00:00:00 CST 2015";
        dateTime = Java8DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "Fri Apr 10 00:00:00 BEIST 2015";
        dateTime = Java8DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "公元2018年03月28日 星期三 18时17分55秒";
        dateTime = Java8DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        date = "2017-01-01 12:00:00.000000001 +0800";
        dateTime = Java8DateTimeUtils.format(date);
        log.info("dateTime:{}", dateTime);


        log.info("{}", new Date());
    }

    @Test
    public void test9() {

        String start = "1970-1-1 00:00:00";


        String text = "2019-03-08 10:29:50";

        SimpleDateFormat sdf = new SimpleDateFormat(DateTimePattern.PATTERN_YYYY_MM_DD);
        try {
            Date date = sdf.parse(text);
            log.info("{}", date.getTime());

            log.info("{}", sdf.parse(start).getTime());

            LocalDateTime localDateTime = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DateTimePattern.PATTERN_YYYY_MM_DD));
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            log.info("{}", instant.toEpochMilli());

            LocalDateTime of = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
            instant = of.atZone(zone).toInstant();
            log.info("{}", instant.toEpochMilli());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test10() {
        String text = "你好 世界!";
        List<String[]> list = Stream.of(text)
                .map(x -> x.split(""))
                .collect(Collectors.toList());
        list.forEach(x -> {
            for (String i : x) {
                log.info("i:{}", i);
            }
        });

        System.out.println("\n");

        List<String> list2 = Stream.of(text)
                .map(x -> x.split(""))
                .flatMap(Arrays::stream)
                // .filter("世"::equals)
                .skip(1)
                .limit(14)
                .collect(Collectors.toList());
        list2.forEach(x -> log.info("x:{}", x));
    }
}
