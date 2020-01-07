package com.iogogogo.kafka.consumer;

import com.iogogogo.common.util.JsonParse;
import com.iogogogo.kafka.pojo.Metric;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by tao.zeng on 2020-01-07.
 * <p>
 * 动态配置多个topic
 * * https://github.com/spring-projects/spring-kafka/issues/361
 */
@Slf4j
@Component
public class MetricConsumer {


    /**
     * 注意这里的topic配置
     * 当然groupId也可以使用SPEL表达书进行配置，这里就不在赘述
     *
     * @param record
     */
    @KafkaListener(topics = "#{'${kafka.producer.topic}'}", groupId = "metric-group")
    public void event(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        kafkaMessage.ifPresent(x -> {
            Metric metric = JsonParse.parse(x, Metric.class);
            log.info("消费kafka中的数据:{}", metric);
        });
    }
}
