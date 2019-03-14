package com.sharplook;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sharplook.entity.CiRelationLogNum;
import com.sharplook.mapper.CiRelationLogNumMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
@MapperScan("com.sharplook.mapper")
public class ScriptLogApplication implements CommandLineRunner {

    @Autowired
    private CiRelationLogNumMapper logNumMapper;

    public static void main(String[] args) {
        SpringApplication.run(ScriptLogApplication.class, args);
    }

    @Override
    public void run(String... args) {

        int count = logNumMapper.count();

        int pageNo = 1;
        int pageSize = 50;

        List<CiRelationLogNum> tmpList = logNumMapper.selectPage(new Page<>(pageNo, pageSize), null).getRecords();
        List<CiRelationLogNum> logNums = new ArrayList<>(tmpList);
        while (logNums.size() != count) {
            pageNo++;
            tmpList = logNumMapper.selectPage(new Page<>(pageNo, pageSize), null).getRecords();
            logNums.addAll(tmpList);
        }

        log.info("{}", logNums.size());
    }
}
