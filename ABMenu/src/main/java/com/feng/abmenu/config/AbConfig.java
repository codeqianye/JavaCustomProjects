package com.feng.abmenu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟 AB 配置中心。
 *
 * 真实项目里这段 JSON 存在配置中心，可以随时修改不发版。
 * 本项目直接在代码里写死，修改后重启即可看到效果。
 *
 * ★ 核心练习：改这里的配置，观察搜索行为如何变化，不需要改任何 Processor 代码。
 */
@Configuration
public class AbConfig {

    @Bean
    public List<ProcessorConfig> abSearchConfig() {
        return Arrays.asList(

            // ── Processor 1：精确名称召回 ─────────────────────────────
            // 同一个 DishRecallProcessor 类，params 不同，行为不同
            /*new ProcessorConfig("DishRecallProcessor", params(
                "toggle",       "1",            // 启用
                "absid",        "dish_exact",   // 追踪标识
                "queryBuilder", "exactName",    // 选 ExactNameQueryBuilder
                "recallType",   "exact",        // 写入 Context 的 key
                "size",         "5"             // 最多返回 5 条
            )),*/

            // ── Processor 2：模糊关键词召回（兜底） ────────────────────
            // 还是同一个 DishRecallProcessor 类，params 换了，变成模糊召回
            /*new ProcessorConfig("DishRecallProcessor", params(
                "toggle",       "1",
                "absid",        "dish_fuzzy",
                "queryBuilder", "fuzzy",        // 选 FuzzyQueryBuilder
                "recallType",   "fuzzy",
                "size",         "10"
            )),*/
            // ★ 练习1：把下面这段注释去掉，加入分类召回，重启测试
//            new ProcessorConfig("DishRecallProcessor", params(
//                    "toggle",       "1",
//                    "absid",        "dish_category",
//                    "queryBuilder", "byCategory",
//                    "recallType",   "category",
//                    "size",         "8"
//            )),

            // ★ 练习2：给精确召回加价格过滤，只返回 40 元以内的菜
            // 修改 Processor 1 的 params，加上："maxPrice", "40"

            // 练习4(20~30)
            new ProcessorConfig("DishRecallProcessor", params(
                    "toggle",       "1",
                    "absid",        "dish_price",
                    "minPrice",     "20",
                    "maxPrice",     "30",
                    "queryBuilder", "byPriceRange",
                    "recallType",   "byPriceRange",
                    "size",         "10"
            )),
            // ── Processor 3：汇总排序 ───────────────────────────────
            new ProcessorConfig("DishSortProcessor", params(
                "toggle", "1",
                "absid",  "dish_sort"
            ))
        );
    }

    /** 辅助方法：key-value 可变参数转 Map */
    private Map<String, String> params(String... kvs) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < kvs.length - 1; i += 2) {
            map.put(kvs[i], kvs[i + 1]);
        }
        return map;
    }
}