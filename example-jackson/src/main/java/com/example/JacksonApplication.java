package com.example;

import com.example.model.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class JacksonApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JacksonApplication.class, args);
    }

    @Override
    public void run(String... args) throws JsonProcessingException {
        Model model = new Model("测试", 2.5, LocalDateTime.now());
        XmlMapper xmlMapper = new XmlMapper();

        String value = xmlMapper.writeValueAsString(model);
        log.info("value:{}", value);
    }
}
