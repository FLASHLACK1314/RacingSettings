package com.flashlack.homeofesportsracingsimulatorsettings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 自定义分页
 * @author FLASHLACK
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomPage<T> {
    private List<T> records;
    private Long total;
    private Long size;
}