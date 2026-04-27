package com.feng.abmenu.controller;

import com.feng.abmenu.abrun.ProcessorRunner;
import com.feng.abmenu.pojo.Dish;
import com.feng.abmenu.pojo.SearchContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搜索接口入口，对应项目里的 SearchSongAbController。
 *
 * 注意：Controller 极薄，只做参数传入和结果返回，没有任何业务逻辑。
 * 所有复杂处理都在 ProcessorRunner 和各 Processor 里。
 */
@Slf4j
@RestController
@RequestMapping("/api/dish")
public class DishSearchController {

    private final ProcessorRunner processorRunner;
    //Spring 看到只有一个构造器，同样自动用它注入
    public DishSearchController(ProcessorRunner processorRunner) {
        this.processorRunner = processorRunner;
    }

    @GetMapping("/search")
    public List<Dish> search(@RequestParam String keyword) {
        long start = System.currentTimeMillis();

        // 1. 触发流水线执行（对应 executeAbStrategy）
        SearchContext ctx = processorRunner.run(keyword);

        // 2. 返回最终结果（对应 afterHandle 的结果组装）
        log.info("搜索 [{}] 完成，耗时 {}ms，结果数 {}",
                keyword, System.currentTimeMillis() - start, ctx.getFinalResult().size());
        return ctx.getFinalResult();
    }
}