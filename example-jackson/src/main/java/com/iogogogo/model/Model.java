package com.iogogogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model implements Serializable {

    private String name;

    private double weight;

    private LocalDateTime localDateTime;
}
