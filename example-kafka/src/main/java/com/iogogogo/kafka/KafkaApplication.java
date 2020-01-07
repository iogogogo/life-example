package com.iogogogo.kafka;

import com.google.common.collect.Maps;
import com.iogogogo.common.util.DecimalUtils;
import com.iogogogo.common.util.JsonParse;
import com.iogogogo.kafka.pojo.Metric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by tao.zeng on 2020-01-06.
 */
@Slf4j
@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 这里对应我们在yml中自定义的配置，用于获取发送数据用的topic
     */
    @Value("${kafka.producer.topic}")
    private String producerTopic;

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        while (true) {
            writer2Kafka(random);
            // 每隔3s向kafka发送数据
            TimeUnit.SECONDS.sleep(3);
        }
    }

    private void writer2Kafka(Random random) {
        Metric metric = new Metric();
        metric.setHostname("NODE-" + random.nextInt(200));
        metric.setTotal(random.nextInt(10000));
        metric.setSuccCnt(random.nextInt(9900));

        float v = DecimalUtils.divide(metric.getSuccCnt(), metric.getTotal());
        metric.setSuccRate(v);
        metric.setStatus(random.nextInt(2));
        metric.setTimestamp(LocalDateTime.now());
        Map<String, Object> tagMap = Maps.newHashMap();
        tagMap.put("cpu_util", random.nextFloat() * 100);
        tagMap.put("mem_util", random.nextFloat() * 100);
        metric.setTags(tagMap);

        String data = JsonParse.toJson(metric);
        // log.info("发送数据:{}", data);
        kafkaTemplate.send(producerTopic, data);
        kafkaTemplate.flush();
    }
}
