package com.iogogogo.common.util;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

/**
 * Created by tao.zeng on 2020-01-14.
 */
public class DateTimeCycleUtils {

    public static Tuple2<LocalDateTime, LocalDateTime> weekRange() {
        return weekRange(LocalDate.now());
    }

    /**
     * 获取指定日期的周起始时间
     *
     * @param date
     * @return
     */
    public static Tuple2<LocalDateTime, LocalDateTime> weekRange(LocalDate date) {
        // 使用WeekFields设置一周的开始为周一
        TemporalField fieldISO = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();
        LocalDateTime startTime = date.with(fieldISO, 1).atTime(LocalTime.MIN);
        LocalDateTime endTime = date.with(fieldISO, 7).atTime(LocalTime.MAX);
        return Tuple.of(startTime, endTime);
    }


    public static Tuple2<LocalDateTime, LocalDateTime> monthRange() {
        return monthRange(LocalDate.now());
    }

    /**
     * 获取指定日期的月起始时间
     *
     * @param date
     * @return
     */
    public static Tuple2<LocalDateTime, LocalDateTime> monthRange(LocalDate date) {
        LocalDateTime startTime = date.atTime(LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime endTime = date.atTime(LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        return Tuple.of(startTime, endTime);
    }


    public static Tuple2<LocalDateTime, LocalDateTime> quarterRange() {
        return quarterRange(LocalDate.now());
    }

    /**
     * 获取指定日期的季度起始时间
     *
     * @param date
     * @return
     */
    public static Tuple2<LocalDateTime, LocalDateTime> quarterRange(LocalDate date) {
        QuarterCycle quarterCycle = new QuarterCycle();
        int quarter = quarterCycle.getQuarter(date);
        return quarterCycle.getQuarterRange(date.getYear(), quarter);
    }

}
