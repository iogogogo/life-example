package com.iogogogo.common.util;

import java.math.BigDecimal;

/**
 * Created by tao.zeng on 2020-01-07.
 */
public class DecimalUtils {

    public static final int DEF_DIV_SCALE = 10;

    public static float divide(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static float divide(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
