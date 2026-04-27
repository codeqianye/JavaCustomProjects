package com.feng.abmenu.process;

import com.feng.abmenu.pojo.SearchContext;

import java.util.Map;

/**
 * 所有 Processor 的公共基类，对应项目里的 AbstractGeneralProcessor。
 *
 * 只声明 process() 入口，不规定流程。
 * - 召回类 Processor：继承 AbstractDishRecallProcessor，走7步模板
 * - 排序类 Processor：直接继承本类，自由 override process()
 *
 * 对应真实项目继承关系：
 *   AbstractGeneralProcessor
 *       ├── AbstractGeneralRecallProcessor（召回骨架，final process）
 *       └── GeneralRuleSortNingProcessor  （排序，直接 override process）
 */
public abstract class AbstractDishProcessor {

    public abstract void process(SearchContext ctx, Map<String, String> params);
}