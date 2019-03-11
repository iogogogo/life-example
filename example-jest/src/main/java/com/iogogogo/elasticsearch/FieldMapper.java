package com.iogogogo.elasticsearch;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tao.zeng on 2019-03-11.
 */
public class FieldMapper {

    private Map<String, Object> fieldMap = new HashMap<>(16);

    public FieldMapper addMapper(String key, Object type) {
        fieldMap.put(key, type);
        return this;
    }

    public Map<String, Object> build() {
        return fieldMap;
    }
}
