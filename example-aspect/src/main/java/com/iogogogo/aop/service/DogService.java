package com.iogogogo.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by tao.zeng on 2020/2/16.
 */
@Slf4j
@Component
public class DogService {

    public String sayHi(String name) {
        String message = String.format("%s在叫", name);
        log.info(message);
        return message;
    }


    public void throwsException() {
        throw new NullPointerException("空指针异常");
    }
}
