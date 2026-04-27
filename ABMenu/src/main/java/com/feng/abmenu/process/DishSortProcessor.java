package com.feng.abmenu.process;

import com.feng.abmenu.pojo.SearchContext;
import com.feng.abmenu.pojo.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇总排序 Processor，对应项目里的排序类 Processor。
 *
 * 职责：把各召回类型的结果合并 → 按 id 去重（保留先出现的，精确召回优先）→ 写入 finalResult。
 *
 * 注意：这个 Processor 的逻辑与召回 Processor 不同，不走父类的模板方法。
 *       它直接 override process()，因为它的工作是"合并汇总"而不是"单路召回"。
 */
@Slf4j
@Component
public class DishSortProcessor extends AbstractDishProcessor {

    /**
     * 直接 override 整个 process()，不走父类的模板步骤。
     * 因为排序不需要 buildQuery/execute 那套流程。
     */
    @Override
    public final void process(SearchContext ctx, Map<String, String> params) {
        if ("0".equals(params.get("toggle"))) return;

        // 合并所有召回类型的结果，按 id 去重，保留先出现的（LinkedHashMap 保证顺序）
        // 精确召回先写入 Context（key="exact"），所以精确结果排在前面
        Map<String, Dish> deduped = new LinkedHashMap<>();
        ctx.getRecalledItems().forEach((recallType, dishes) -> {
            log.info("合并召回类型 {}：{}条", recallType, dishes.size());
            dishes.forEach(dish -> deduped.putIfAbsent(dish.getId(), dish));
        });

        List<Dish> sorted = new ArrayList<>(deduped.values());
        ctx.setFinalResult(sorted);
        log.info("最终结果：{}条（去重后）", sorted.size());
    }
}