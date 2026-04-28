package com.feng.abmenu.builder;

import com.feng.abmenu.pojo.SearchContext;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class PriceRangeQueryBuilder implements DishQueryBuilder{
    @Override
    public String key() {
        return "byPriceRange";
    }

    @Override
    public String build(SearchContext ctx, Map<String, String> params) {
        return params.get("minPrice") + "-" + params.get("maxPrice"); //返回价格范围字符串，例如 "10-20"
    }
}
