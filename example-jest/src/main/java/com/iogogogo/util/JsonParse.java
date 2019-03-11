package com.iogogogo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;

/**
 * Created by tao.zeng on 2019-03-11.
 */
public class JsonParse {

    /**
     * 对象装换成json字符串
     *
     * @param data     需要序列化的数据
     * @param features 可选参数 用于指定序列化模式
     *                 <p>
     *                 QuoteFieldNames———-输出key时是否使用双引号,默认为true
     *                 WriteMapNullValue——–是否输出值为null的字段,默认为false
     *                 WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
     *                 WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
     *                 WriteNullStringAsEmpty—字符类型字段如果为null,输出为""而非null
     *                 WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
     *                 </p>
     * @return
     */
    public static String toJson(Object data, SerializerFeature... features) {
        return JSON.toJSONString(data, features);
    }

    public static <T> T parse(String json, Class<T> clz) {
        return JSON.parseObject(json, clz);
    }

    public static <T> T parse(String json, Type type) {
        return JSON.parseObject(json, type);
    }
}
