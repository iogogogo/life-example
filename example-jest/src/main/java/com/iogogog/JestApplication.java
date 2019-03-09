package com.iogogog;

import com.iogogog.elasticsearch.ElasticsearchService;
import com.iogogog.elasticsearch.util.BatchProcess;
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

    @Autowired
    private ElasticsearchService elasticsearchService;

    public static void main(String[] args) {
        SpringApplication.run(JestApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Map<String, Map<String, Object>> mapping = new HashMap<>(16);

        String index = "test_001";
        List<Model> list = new ArrayList<>();
        for (int i = 0; i < 700009; i++) {
            list.add(new Model("小花脸 — " + UUID.randomUUID().toString()));
        }
        try {

            JestResult result = elasticsearchService.indicesExists(index);
            log.info("indicesExists:{}", result.isSucceeded());

            result = elasticsearchService.deleteIndex(index);
            log.info("deleteIndex:{}", result.isSucceeded());

            result = elasticsearchService.indicesExists(index);
            log.info("indicesExists:{}", result.isSucceeded());

            result = elasticsearchService.createIndex(index);
            log.info("createIndex:{}", result.isSucceeded());


            Map<String, Object> properties = new HashMap<>(16);
            properties.put("name", "text");
            mapping.put("properties", properties);

            elasticsearchService.createMappingAsync(index, "test", mapping, null);

            result = elasticsearchService.save(index, "test", String.valueOf(1024), new Model("小花脸"));
            log.info("save:{}", result.isSucceeded());

            BatchProcess.process(list, 50000, data -> {

                elasticsearchService.batchSaveAsync(index, "test", null, data, null);

//                JestResult jestResult = elasticsearchService.batchSave(index, "test", null, data);
//                log.info("batchSave:{}", jestResult.isSucceeded());
            });

        } catch (Exception e) {
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
