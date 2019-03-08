package com.sharplook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ScriptLogApplication implements CommandLineRunner {

    @Value("${}")
    private String dirPath;

    public static void main(String[] args) {
        SpringApplication.run(ScriptLogApplication.class, args);
    }

    @Override
    public void run(String... args) {
        File file = new File(dirPath);
        File[] files = file.listFiles();

    }
}
