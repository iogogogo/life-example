package com.iogogogo.common.util;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

/**
 * Created by tao.zeng on 2020-01-03.
 */
public class QuarterCycle {


    /**
     * 定义四个季度枚举值
     */
    enum QuarterEnum {
        FIRST, SECOND, THIRD, FOURTH
    }

    class QuarterOfYearQuery implements TemporalQuery<QuarterEnum> {
        @Override
        public QuarterEnum queryFrom(TemporalAccessor temporal) {
            LocalDate now = LocalDate.from(temporal);
            if (now.isBefore(now.with(Month.APRIL).withDayOfMonth(1))) {
                return QuarterEnum.FIRST;
            } else if (now.isBefore(now.with(Month.JULY).withDayOfMonth(1))) {
                return QuarterEnum.SECOND;
            } else if (now.isBefore(now.with(Month.OCTOBER).withDayOfMonth(1))) {
                return QuarterEnum.THIRD;
            } else {
                return QuarterEnum.FOURTH;
            }
        }
    }

    /**
     * 根据 LocalDateTime 获取季度
     *
     * @param dateTime
     * @return
     */
    public int getQuarter(LocalDateTime dateTime) {
        return getQuarter(dateTime.toLocalDate());
    }

    /**
     * 根据 LocalDate 获取季度
     *
     * @param date
     * @return
     */
    public int getQuarter(LocalDate date) {
        TemporalQuery<QuarterEnum> quarterOfYearQuery = new QuarterOfYearQuery();
        QuarterEnum quarter = date.query(quarterOfYearQuery);
        switch (quarter) {
            case FIRST:
                return 1;
            case SECOND:
                return 2;
            case THIRD:
                return 3;
            case FOURTH:
                return 4;
        }
        return 0;
    }


    public Tuple2<LocalDateTime, LocalDateTime> getQuarterRange(LocalDateTime dateTime, int quarter) {
        return getQuarterRange(dateTime.getYear(), quarter);
    }

    public Tuple2<LocalDateTime, LocalDateTime> getQuarterRange(LocalDate dateTime, int quarter) {
        return getQuarterRange(dateTime.getYear(), quarter);
    }

    /**
     * 获取某年某季度的第一天和最后一天
     *
     * @param year    哪一年
     * @param quarter 第几季度
     */
    public Tuple2<LocalDateTime, LocalDateTime> getQuarterRange(int year, int quarter) {
        LocalDate startDate, endDate;
        switch (quarter) {
            case 1:
                // 01.01-03.31
                startDate = LocalDate.of(year, 1, 1);
                endDate = LocalDate.of(year, 3, 31);
                break;
            case 2:
                // 04.01-06.30
                startDate = LocalDate.of(year, 4, 1);
                endDate = LocalDate.of(year, 6, 30);
                break;
            case 3:
                // 07.01-09.30
                startDate = LocalDate.of(year, 7, 1);
                endDate = LocalDate.of(year, 9, 30);
                break;
            case 4:
                // 10.01-12.31
                startDate = LocalDate.of(year, 10, 1);
                endDate = LocalDate.of(year, 12, 31);
                break;
            default:
                throw new RuntimeException("quarter range [1-4]");
        }
        return Tuple.of(startDate.atTime(LocalTime.MIN), endDate.atTime(LocalTime.MAX));
    }
}
