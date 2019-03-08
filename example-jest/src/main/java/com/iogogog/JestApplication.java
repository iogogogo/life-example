package com.iogogog;

import com.alibaba.fastjson.JSONObject;
import com.iogogog.elasticsearch.ElasticsearchService;
import io.searchbox.client.JestResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tao.zeng on 2019-03-08.
 */
@Slf4j
@SpringBootApplication
public class JestApplication implements CommandLineRunner {

    @Autowired
    private ElasticsearchService elasticsearchService;

    public static void main(String[] args) {
        SpringApplication.run(JestApplication.class, args);
    }

    @Override
    public void run(String... args) {

        String index = "test_001";
        List<Model> list = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            list.add(new Model("小花脸 — " + UUID.randomUUID().toString()));
        }
        try {

            JestResult result = elasticsearchService.deleteIndex(index);
            log.info("deleteIndex:{}", result.isSucceeded());

            result = elasticsearchService.createIndex(index);
            log.info("createIndex:{}", result.isSucceeded());


            JSONObject mapping = new JSONObject().fluentPut("properties",
                    new JSONObject().fluentPut("name", "text"));
            result = elasticsearchService.createMapping(index, "test", mapping);
            log.info("createMapping:{}", result.isSucceeded());

            result = elasticsearchService.save(index, "test", String.valueOf(1024), new Model("小花脸"));
            log.info("save:{}", result.isSucceeded());

            result = elasticsearchService.batchSave(index, "test", null, list);
            log.info("batchSave:{}", result.isSucceeded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Model {
        String name;
    }
}
