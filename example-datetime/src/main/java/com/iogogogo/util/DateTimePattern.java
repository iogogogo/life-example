package com.iogogogo.util;

public interface DateTimePattern {

    String YYYY_MM_DD = "yyyyMMdd";


    /**
     * yyyy-MM-dd HH:mm:ss
     */
    String PATTERN_YYYY_MM_DD = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss.SSSSSSSSS Z
     * <p>
     * 1996-05-18 23:20:50.000000000 +0800
     */
    String PATTERN_001 = "yyyy-MM-dd HH:mm:ss.SSSSSSSSS Z";

    /**
     * E MMM dd HH:mm:ss yyyy
     * <p>
     * Fri Apr 10 00:00:00 2015
     */
    String PATTERN_002 = "E MMM dd HH:mm:ss yyyy";


    /**
     * E MMM dd HH:mm:ss z yyyy
     * <p>
     * Fri Apr 10 00:00:00 GMT+08:00 2015
     * Fri Apr 10 00:00:00 CST 2015
     * Fri Apr 10 00:00:00 BEIST 2015  => replace("BEIST", "GMT+08:00")
     */
    String PATTERN_003 = "E MMM dd HH:mm:ss z yyyy";


    /**
     * 公元yyyy年MM月dd日 E HH时mm分ss秒
     * <p>
     * 公元2018年03月28日 星期三 18时17分55秒
     */
    String PATTERN_004 = "公元yyyy年MM月dd日 E HH时mm分ss秒";

}
