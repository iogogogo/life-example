package com.iogogogo;

import com.google.gson.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tao.zeng on 2020-01-07.
 */
@Slf4j
public class Java8DateTests {

    @Test
    public void test1() {
        DateMetric dateMetric = new DateMetric();
        dateMetric.setLocalDate(LocalDate.now());
        dateMetric.setLocalDateTime(LocalDateTime.now());

        // serializer
        Gson gson = new Gson();
        String json = gson.toJson(dateMetric);
        log.info("serializer:{}", json);

        // deserializer
        log.info("deserializer:{}", gson.fromJson(json, DateMetric.class));
    }

    @Test
    public void test2() {
        DateMetric dateMetric = new DateMetric();
        dateMetric.setLocalDate(LocalDate.now());
        dateMetric.setLocalDateTime(LocalDateTime.now());

        // serializer
        Gson gson = new GsonBuilder()
                .setPrettyPrinting() // 设置输出格式格式化显示
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        String json = gson.toJson(dateMetric);
        log.info("serializer:\n{}", json);

        // deserializer
        log.info("deserializer:{}", gson.fromJson(json, DateMetric.class));
    }

    private final static String JSON_STRING = "{\"localDate\":{\"year\":2020,\"month\":1,\"day\":7},\"localDateTime\":{\"date\":{\"year\":2020,\"month\":1,\"day\":7},\"time\":{\"hour\":22,\"minute\":9,\"second\":1,\"nano\":271000000}}}";
    private final static String JSON_STRING_ARRAY = "[{\"localDate\":{\"year\":2020,\"month\":1,\"day\":7},\"localDateTime\":{\"date\":{\"year\":2020,\"month\":1,\"day\":7},\"time\":{\"hour\":22,\"minute\":9,\"second\":1,\"nano\":271000000}}}]";


    @Test
    public void jsonObjectFormatter() {
        JsonObject jsonObject = JsonParser.parseString(JSON_STRING).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jsonObject));
    }

    @Test
    public void jsonArrayFormatter() {
        JsonArray jsonArray = JsonParser.parseString(JSON_STRING_ARRAY).getAsJsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jsonArray));
    }


    @Data
    private class DateMetric implements Serializable {

        private LocalDate localDate;

        private LocalDateTime localDateTime;
    }

    /**
     * 处理LocalDate的序列化与反序列化
     */
    final static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        @Override
        public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            String timestamp = element.getAsJsonPrimitive().getAsString();
            return LocalDate.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    /**
     * 处理LocalDateTime序列化与反序列化
     */
    final static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        @Override
        public LocalDateTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            String timestamp = element.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
