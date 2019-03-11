package com.iogogogo;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by tao.zeng on 2019-03-11.
 */
@Slf4j
public class Test {

    @org.junit.Test
    public void test() throws IOException {
        //创建索引中的mapping
        XContentBuilder mappings = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("test").endObject()
                .startObject("properties").endObject()
                .startObject("name").field("type", "integer").field("store","yes").endObject();

        log.info("{}", mappings);
    }
}
