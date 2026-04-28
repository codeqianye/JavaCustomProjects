package com.feng.abmenu.abrun;

import com.feng.abmenu.config.ProcessorConfig;
import com.feng.abmenu.pojo.SearchContext;
import com.feng.abmenu.process.AbstractDishProcessor;
import com.feng.abmenu.process.DishRecallProcessor;
import com.feng.abmenu.process.DishSortProcessor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 流水线驱动器，对应项目里的 RecommendJobLaunchDelegate.run()。
 *
 * 职责：读取 AB 配置列表 → 按顺序实例化 Processor → 依次执行。
 *
 * 真实项目里通过反射 + SpringContextHolder 动态获取 Processor，
 * 本项目简化为 switch-case，原理相同。
 */
@Slf4j
@Component
public class ProcessorRunner {

    private final DishRecallProcessor dishRecallProcessor;
    private final DishSortProcessor dishSortProcessor;
    private final List<ProcessorConfig> abSearchConfig;


    /**
     * processorRunner 用的是构造器注入，没有 @Autowired 但 Spring 会自动识别
     * Spring 启动时扫描到 @Component 的,ProcessorRunner，
     * 发现它只有一个构造器，就自动用这个构造器注入所有参数。List<ProcessorConfig> 来自 AbConfig.java 里的 @Bean
     * @param dishRecallProcessor
     * @param dishSortProcessor
     * @param abSearchConfig
     */
    public ProcessorRunner(DishRecallProcessor dishRecallProcessor,
                           DishSortProcessor dishSortProcessor,
                           List<ProcessorConfig> abSearchConfig) {
        this.dishRecallProcessor = dishRecallProcessor;
        this.dishSortProcessor = dishSortProcessor;
        this.abSearchConfig = abSearchConfig;
    }

    /**
     * 根据类型标识找 Processor 实例。
     * 真实项目用反射 + SpringContextHolder.getBean(Class)，这里简化为 switch。
     */
    private AbstractDishProcessor resolveProcessor(String processorType) {
        switch (processorType) {
            case "DishRecallProcessor": return dishRecallProcessor;
            case "DishSortProcessor":   return dishSortProcessor;
            default:
                log.error("未知 Processor 类型：{}", processorType);
                return null;
        }
    }

    /**
     * 按 AB 配置顺序执行所有 Processor，返回填充好结果的 Context。
     */
    public SearchContext run(String keyword) {
        SearchContext ctx = new SearchContext(keyword);
        for (ProcessorConfig config : abSearchConfig) {
            AbstractDishProcessor processor = resolveProcessor(config.getProcessorType());
            if (processor == null) {
                log.warn("未找到 Processor 类型：{}", config.getProcessorType());
                continue;
            }
            // 每个 Processor 拿到自己的 params 去执行
            // 同一个 DishRecallProcessor 实例，不同 params，不同行为
            processor.process(ctx, config.getParams());
        }
        return ctx;
    }
}