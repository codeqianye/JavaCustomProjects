package com.feng.abmenu.builder;

import com.feng.abmenu.common.SearchContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 模糊匹配
 */
@Component
public class FuzzyQueryBuilder implements DishQueryBuilder{
    @Override
    public String key() {
        return "fuzzy";
    }

    @Override
    public String build(SearchContext ctx, Map<String, String> params) {
        //加 * 前后缀，模糊匹配
        return "*" + ctx.getKeyword() + "*";
    }
}
