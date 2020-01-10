package com.iogogogo;

import io.vavr.API;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static io.vavr.API.$;
import static io.vavr.API.Case;

/**
 * Created by tao.zeng on 2020-01-09.
 */
public class TupleTests {

    @Test
    public void test1() {
        Tuple3<String, LocalDateTime, Long> tuple3 = Tuple.of("TupleTests", LocalDateTime.now(), Long.MAX_VALUE);

        System.out.println(tuple3);

        tuple3 = tuple3.update1("哈哈哈哈");
        System.out.println(tuple3);
    }

    @Test
    public void test2() {
        Tuple2<LocalDateTime, LocalDateTime> tuple2 = getNowRange();

        // 这里通过 _n 或者 _n() 获取每一个元组中的一列数据
        System.out.println("开始时间:" + tuple2._1());
        System.out.println("结束时间:" + tuple2._2);


    }

    @Test
    public void test3() {
        int x = 2;
        String output = API.Match(x).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(3), "three"),
                Case($(), "?"));

        System.out.println(output);
    }


    private Tuple2<LocalDateTime, LocalDateTime> getNowRange() {
        LocalDate now = LocalDate.now();
        // 一天中从零时开始
        LocalDateTime startTime = now.atTime(LocalTime.MIN);
        // 一天到23:59:59秒结束
        LocalDateTime endTime = now.atTime(LocalTime.MAX);
        return Tuple.of(startTime, endTime);
    }
}
