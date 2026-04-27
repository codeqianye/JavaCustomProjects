package com.feng.abmenu.builder;

import com.feng.abmenu.pojo.SearchContext;

import java.util.Map;

/**
 * 查询条件构建器接口
 */
public interface DishQueryBuilder {
    //唯一标识，对应ab中queryBuilder参数值
    String key();

    //构建查询条件字符串
    String build(SearchContext ctx, Map<String,String> params);
}
