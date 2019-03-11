package com.iogogogo.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tao.zeng on 2019-03-11.
 */
public class Mapping {

    private String properties;
    private String type;


    public Mapping(String type) {
        this.properties = "properties";
        this.type = type;
    }

    public Mapping(String properties, String type) {
        this.properties = properties;
        this.type = type;
    }

    private Map<String, Map<String, Map<String, Map<String, Object>>>> mapping = new HashMap<>();
    private Map<String, Map<String, Map<String, Object>>> property = new HashMap<>();
    private static Map<String, Map<String, Object>> fields = new HashMap<>();

    public Map<String, Map<String, Map<String, Map<String, Object>>>> build() {
        property.put(properties, fields);
        mapping.put(type, property);
        return this.mapping;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Fields {

        private String field;

        private Map<String, Object> mapping;

        public Map<String, Map<String, Object>> build() {
            fields.put(field, mapping);
            return Mapping.fields;
        }
    }
}
