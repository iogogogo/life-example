package com.iogogogo.security.util;

import com.alibaba.fastjson.JSON;
import com.iogogogo.security.common.ResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tao.zeng on 2019-03-25.
 */
public class ResourcesUtils {

    public static void writer(ResponseWrapper wrapper, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 状态
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(wrapper));
    }
}
