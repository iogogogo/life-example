package com.iogogogo;

import com.iogogogo.elasticsearch.ElasticsearchService;
import com.iogogogo.util.BatchProcess;
import com.iogogogo.util.JsonParse;
import io.searchbox.client.JestResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * Created by tao.zeng on 2019-03-08.
 */
@Slf4j
@SpringBootApplication
public class JestApplication implements CommandLineRunner {

    private final static String INDEX = "test_002";
    private final static String TYPE = "model";

    @Autowired
    private ElasticsearchService elasticsearchService;

    public static void main(String[] args) {
        SpringApplication.run(JestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Model> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(new Model("小花脸 — " + UUID.randomUUID().toString()));
        }

        JestResult result = elasticsearchService.indicesExists(INDEX);
        log.info("indicesExists:{}", result.isSucceeded());

        result = elasticsearchService.deleteIndex(INDEX);
        log.info("deleteIndex:{}", result.isSucceeded());

        Map<String, Map<String, Map<String, Map<String, Object>>>> mapping = new HashMap<>();
        Map<String, Map<String, Map<String, Object>>> properties = new HashMap<>();

        Map<String, Map<String, Object>> fields = new HashMap<>();
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("type", "string");
        mapper.put("store", "yes");
        fields.put("name", mapper);

        mapper = new HashMap<>();
        mapper.put("type", "integer");
        fields.put("age", mapper);

        properties.put("properties", fields);
        mapping.put(TYPE, properties);


        elasticsearchService.createMappingAsync(INDEX, TYPE, JsonParse.toJson(mapping), null);

        result = elasticsearchService.createIndex(INDEX);
        log.info("createIndex:{}", result.isSucceeded());
        Model model = new Model("小花脸");
        elasticsearchService.saveAsync(INDEX, TYPE, null, model, null);
        BatchProcess.process(list, 500,
                x -> elasticsearchService.batchSaveAsync(INDEX, TYPE, null, x, null));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Model {
        String name;
    }

}
