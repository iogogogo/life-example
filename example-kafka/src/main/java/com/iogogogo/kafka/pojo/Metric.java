package com.iogogogo.kafka.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by tao.zeng on 2020-01-07.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metric implements Serializable {

    private String hostname;

    private long total;

    @SerializedName("succ_cnt")
    private long succCnt;

    @SerializedName("succ_rate")
    private float succRate;

    private int status;

    private LocalDateTime timestamp;

    private Map<String, Object> tags;
}
