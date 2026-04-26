package com.feng.abmenu.builder;

import com.feng.abmenu.common.SearchContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 按分类匹配
 */
@Component
public class CategoryQueryBuilder implements DishQueryBuilder{

    @Override
    public String key() {
        return "byCategory";
    }

    @Override
    public String build(SearchContext ctx, Map<String, String> params) {
        return ctx.getKeyword();
    }
}
