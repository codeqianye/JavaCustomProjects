package com.feng.abmenu.process;

import com.feng.abmenu.common.SearchContext;
import com.feng.abmenu.pojo.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 菜品搜索处理器骨架，对应项目里的 AbstractGeneralRecallProcessor。
 * ★ 核心思想：骨架（process 方法）固定执行步骤，子类决定每步怎么做。
 * 固定流程：
 *   toggle 开关检查 → preCheck → buildQuery → execute → mapItems → postProcess → 写入 Context
 */
@Slf4j
public abstract class AbstractDishProcessor {

    /**
     * 模板方法：固定执行步骤，不允许子类重写(final控制)
     * @param ctx
     * @param params
     */
    public final void process(SearchContext ctx, Map<String,String> params){

        //step1: 开关控制,toggle=0 直接跳过这个process
        if("0".equals(params.get("toggle"))){
            log.info("[{}] toggle=0, skip process", params.getOrDefault("absid","unknow"));
            return; //开关关闭，直接返回
        }

        //step2: 前置校验(Hook,默认通过，子类可以重写)
        if(!preCheck(ctx,params)){
            log.info("[{}] preCheck 不通过, skip process", params.getOrDefault("absid","?"));
            return;
        }

        // Step 3：构建查询条件（抽象方法，子类必须实现）
        String query = buildQuery(ctx, params);
        if(!StringUtils.hasText(query)){
            log.info("[{}] 构建查询条件失败, skip process", params.getOrDefault("absid","?"));
            return;
        }

        // Step 4：执行搜索（抽象方法，子类必须实现）
        List<Dish> rawResult = execute(ctx, query, params);
        if(CollectionUtils.isEmpty(rawResult)){
            return;
        }

        // Step 5：结果映射（Hook，默认原样返回，子类可 override 做字段转换）
        List<Dish> items = mapItems(rawResult, params);

        // Step 6：后置处理（Hook，默认原样返回，子类可 override 做过滤/加工）
        items = postProcess(ctx, items, params);

        // Step 7：写入 Context（固定逻辑，对应 resultHandle()）
        String recallType = params.getOrDefault("recallType", "default");
        ctx.putRecalledItems(recallType, items);
        log.info("[{}] 召回完成，recallType={}，结果数={}", params.getOrDefault("absid", "?"),
                recallType, items.size());
    }

    /**
     * ===============抽象方法：子类必须实现===============
     */
    //构建查询语句
    protected abstract String buildQuery(SearchContext ctx, Map<String,String> params);
    /** 执行搜索，返回原始结果，对应 executeSearch() + mapItems() 的搜索部分 */
    protected abstract List<Dish> execute(SearchContext ctx, String query, Map<String, String> params);

    /**
     * ===============Hook 方法：子类可选 override==============
     */


    /**
     * 前置校验，对应 preCheck()。
     * 默认通过，子类可 override 添加拦截，返回 false 则跳过本 Processor。
     */
    protected boolean preCheck(SearchContext ctx, Map<String, String> params) {
        return true;
    }

    /**
     * 结果映射，对应 mapItems()。
     * 默认原样返回，子类可 override 做字段提取/转换。
     */
    protected List<Dish> mapItems(List<Dish> rawResult, Map<String, String> params) {
        return rawResult;
    }

    /**
     * 后置处理，对应 postProcess()。
     * 默认原样返回，子类可 override 做过滤（如日期过滤、价格过滤）。
     */
    protected List<Dish> postProcess(SearchContext ctx, List<Dish> items, Map<String, String> params) {
        return items;
    }
}
