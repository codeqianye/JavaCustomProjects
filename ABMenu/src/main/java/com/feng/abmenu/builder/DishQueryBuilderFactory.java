package com.feng.abmenu.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DishQueryBuilderFactory {
    private final List<DishQueryBuilder> builders;

    /**
     * Spring自动注入：启动时扫描容器,把所有 DishQueryBuilder 实现放进这个 List
     * 当前有3个实现(Exact/Category/Fuzzy)，将来加新的自动收录
     */
    @Autowired
    public DishQueryBuilderFactory(List<DishQueryBuilder> builders) {
        this.builders = builders == null ? Collections.emptyList() : builders;
    }

    /**
     * 按params 里的 queryBuilder 参数选择对应的builder
     * 找不到，返回null兜底
     * @param params
     * @return
     */
    public DishQueryBuilder choose(Map<String,String> params){
        String key = params.get("queryBuilder");
        if(!StringUtils.hasText(key)){
            return null;
        }
        return builders.stream().filter(b -> b.key()
                .equalsIgnoreCase(key)).findFirst().orElse(null);
    }
}
