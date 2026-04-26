package com.feng.abmenu.esquery;

import com.feng.abmenu.pojo.Dish;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内存数据仓库，模拟 ES 查询。
 * 真实项目里这里是 ES HTTP 请求，本项目用 Java Stream 过滤代替。
 */
@Repository
public class DishRepository {

    // 模拟 ES 索引里的数据
    private static final List<Dish> ALL_DISHES = Arrays.asList(
        new Dish("1",  "宫保鸡丁",   "川菜", "鸡肉 花生 辣椒", 38.0),
        new Dish("2",  "夫妻肺片",   "川菜", "牛肉 红油 花生", 45.0),
        new Dish("3",  "麻婆豆腐",   "川菜", "豆腐 肉末 辣椒", 28.0),
        new Dish("4",  "白切鸡",     "粤菜", "鸡肉 姜葱",     58.0),
        new Dish("5",  "清蒸鲈鱼",   "粤菜", "鲈鱼 姜葱 豉油", 88.0),
        new Dish("6",  "剁椒鱼头",   "湘菜", "鱼头 剁椒",     68.0),
        new Dish("7",  "辣椒炒肉",   "湘菜", "猪肉 辣椒",     35.0),
        new Dish("8",  "鸡丁炒饭",   "主食", "鸡丁 米饭 鸡蛋", 22.0),
        new Dish("9",  "番茄鸡蛋汤", "汤类", "番茄 鸡蛋",     18.0),
        new Dish("10", "扬州炒饭",   "主食", "米饭 火腿 虾仁", 25.0)
    );

    /** 精确名称匹配（对应 ES 的 term query） */
    public List<Dish> findByNameExact(String name) {
        return ALL_DISHES.stream()
                .filter(d -> d.getName().equals(name))
                .collect(Collectors.toList());
    }

    /** 按分类匹配（对应 ES 的 filter query） */
    public List<Dish> findByCategory(String category) {
        return ALL_DISHES.stream()
                .filter(d -> d.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /** 模糊匹配：名称或标签包含关键词（对应 ES 的 match/wildcard query） */
    public List<Dish> findByKeyword(String keyword) {
        return ALL_DISHES.stream()
                .filter(d -> d.getName().contains(keyword) || d.getTags().contains(keyword))
                .collect(Collectors.toList());
    }
}