package com.feng.abmenu.common;

import com.feng.abmenu.pojo.Dish;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 共享上下文
 */
@Data
public class SearchContext {
    //关键词
    private String keyword;

    //key: type,召回类型
    //为什么用 LinkedHashMap？保证插入顺序，精确召回先插入，后续合并时精确结果排在前面。
    private Map<String, List<Dish>> recalledItems = new LinkedHashMap<>();

    //最终汇总排序后的结果（SortProcessor）
    private List<Dish> finalResult = new ArrayList<>();

    public SearchContext(String keyword) {
        this.keyword = keyword;
    }
    public void putRecalledItems(String type, List<Dish> items) {
        this.recalledItems.put(type, items);
    }
}
