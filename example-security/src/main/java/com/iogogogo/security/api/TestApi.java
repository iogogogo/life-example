package com.iogogogo.security.api;

import com.iogogogo.security.common.ResponseWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@RestController
public class TestApi {

    // 测试普通权限
    @RequestMapping(value = "/normal/test", method = RequestMethod.GET)
    public ResponseWrapper test1() {
        return ResponseWrapper.builder().data("/normal/test接口调用成功！").build();
    }

    // 测试管理员权限
    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public ResponseWrapper test2() {
        return ResponseWrapper.builder().data("/admin/test接口调用成功！").build();

    }
}

