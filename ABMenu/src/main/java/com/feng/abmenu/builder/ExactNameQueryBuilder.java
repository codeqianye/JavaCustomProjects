package com.feng.abmenu.builder;

import com.feng.abmenu.common.SearchContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 精确名称匹配
 */
@Component //关键：有了@Component,工厂的List<DishQueryBuilder> 才能自动收集到它
public class ExactNameQueryBuilder implements DishQueryBuilder{
    @Override
    public String key() {
        return "exactName";
    }

    @Override
    public String build(SearchContext ctx, Map<String, String> params) {
        return ctx.getKeyword(); //直接返回用户输入的关键词，作为精确匹配条件
    }
}
