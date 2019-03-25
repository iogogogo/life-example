package com.iogogogo.security.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by tao.zeng on 2019-03-25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> implements Serializable {


    private int code = 200;

    private String message = "successful!";

    private T data;
}
