package com.feng.abmenu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dish {
    private String id;
    private String name;        // 菜名，对应 song.name
    private String category;    // 分类（川菜/粤菜/湘菜），类似 song.tags
    private String tags;        // 食材标签，类似 song.alias
    private double price;       // 价格，类似 song.hot（用于过滤/排序的数值字段）
}