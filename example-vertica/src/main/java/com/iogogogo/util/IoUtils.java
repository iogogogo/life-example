package com.iogogogo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tao.zeng on 2019-03-12.
 */
public class IoUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(IoUtils.class);

    private IoUtils() {
    }

    /**
     * 关闭io
     *
     * @param closeables io 对象 stream、Closeable
     */
    public static void close(Closeable... closeables) {
        if (closeables != null) {
            try {
                for (Closeable io : closeables) {
                    if (io != null) io.close();
                }
            } catch (IOException e) {
                // ignore
                LOGGER.warn("关闭io异常:{}", e);
            }
        }
    }

}
