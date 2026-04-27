package com.feng.abmenu.process;

import com.feng.abmenu.builder.DishQueryBuilder;
import com.feng.abmenu.builder.DishQueryBuilderFactory;
import com.feng.abmenu.pojo.SearchContext;
import com.feng.abmenu.esquery.DishRepository;
import com.feng.abmenu.pojo.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 核心召回 Processor，对应项目里的 GeneralSearchSongProcessor。
 *
 * ★ 最重要的理解点：
 *   这个类在 AB 配置里出现 N 次，每次 params 不同，行为完全不同。
 *   - params: {queryBuilder:"exactName"} → 精确名称召回
 *   - params: {queryBuilder:"fuzzy"}     → 模糊关键词召回
 *   - params: {queryBuilder:"byCategory"} → 分类召回
 *   一个类，三种行为，靠配置区分。
 */
@Slf4j
@Component
public class DishRecallProcessor extends AbstractDishRecallProcessor {
    private final DishQueryBuilderFactory queryBuilderFactory;
    private final DishRepository dishRepository;

    public DishRecallProcessor(DishQueryBuilderFactory queryBuilderFactory,
                               DishRepository dishRepository) {
        this.queryBuilderFactory = queryBuilderFactory;
        this.dishRepository = dishRepository;
    }

    /**
     * 构建查询条件：用工厂params里的 queryBuilder 参数，拿到对应的 QueryBuilder 实例，构建查询条件。
     */
    @Override
    protected String buildQuery(SearchContext ctx, Map<String, String> params) {
        DishQueryBuilder builder = queryBuilderFactory.choose(params);
        if(builder == null){
            log.warn("未找到合适的 QueryBuilder，params:{}", params);
            return "";
        }
        String query = builder.build(ctx, params);
        log.info("构建查询条件，使用 QueryBuilder: {}, query: {}", builder.key(), query);
        return query;
    }

    @Override
    protected List<Dish> execute(SearchContext ctx, String query, Map<String, String> params) {
        String queryBuilder = params.getOrDefault("queryBuilder", "");
        int size = Integer.parseInt(params.getOrDefault("size", "10"));
        List<Dish> result;
        switch (queryBuilder){
            case "exactName":
                result = dishRepository.findByNameExact(query);
                break;
            case "fuzzy":
                // FuzzyQueryBuilder 在词前后加了 *，这里去掉做 contains 搜索
                String keyword = query.replace("*", "");
                result = dishRepository.findByKeyword(keyword);
                break;
            case "byCategory":
                result = dishRepository.findByCategory(query);
                break;
            default:
                log.warn("未知的 queryBuilder: {}, params: {}", queryBuilder, params);
                result = Collections.emptyList();
        }
        //控制返回数量
        return result.size() > size ? result.subList(0, size) : result;
    }

    /**
     * 前置校验：关键词为空直接跳过，不浪费搜索资源。
     */
    @Override
    protected boolean preCheck(SearchContext ctx, Map<String, String> params) {
        String keyword = ctx.getKeyword();
        if (!StringUtils.hasText(keyword)) {
            log.warn("[{}] 关键词为空，跳过召回", params.getOrDefault("absid", "?"));
            return false;
        }
        return true;
    }

    /**
     * 后置处理：如果 AB 配置了价格上限，过滤超出的菜品。
     * 对应 postProcess() Hook，用于演示"后置过滤"的用法。
     * （票务 Processor 在这里做日期过滤，原理相同）
     */
    @Override
    protected List<Dish> postProcess(SearchContext ctx, List<Dish> items, Map<String, String> params) {
        String maxPriceStr = params.get("maxPrice");
        if (maxPriceStr == null) return items;

        double maxPrice = Double.parseDouble(maxPriceStr);
        List<Dish> collect = items.stream().filter(item -> item.getPrice() <= maxPrice).collect(Collectors.toList());
        log.info("[{}] 价格过滤：{}条 → {}条（maxPrice={}）",
                params.getOrDefault("absid", "?"), items.size(), collect.size(), maxPrice);
        return collect;
    }
}
