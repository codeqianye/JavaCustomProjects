package com.feng.abmenu.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * ab配置
 * 一个process 的配置，对应真实项目 AB配置 json的一个条目
 */
@Data
@AllArgsConstructor
public class ProcessorConfig {
    //处理器类型标识
    private String processorType;
    /**
     * 处理器参数 对应parmas
     * 真实项目中来自配置中心，可以随时修改不发版
     * 本项目直接在ABconfig.java中写死
     */
    private Map<String,String> params;
}
