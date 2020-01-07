package com.iogogogo.common.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tao.zeng on 2020-01-06.
 */
public class JsonParse {

    private final static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static String toJson(Object bean) {
        return GSON.toJson(bean);
    }

    public static String toJson(GsonBuilder builder, Object bean) {
        return builder.create().toJson(bean);
    }

    public static <T> T parse(String json, Class<T> clz) {
        return GSON.fromJson(json, clz);
    }

    public static <T> T parse(GsonBuilder builder, String json, Class<T> clz) {
        return builder.create().fromJson(json, clz);
    }

    public static <T> T parse(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T parse(GsonBuilder builder, String json, Type type) {
        return builder.create().fromJson(json, type);
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
