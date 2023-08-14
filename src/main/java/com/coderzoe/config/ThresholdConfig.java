package com.coderzoe.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阈值配置，当服务器指标阈值
 * @author coderZoe
 * @date 2023/8/14 11:36
 */
@Configuration
@ConfigurationProperties(prefix = "threshold")
@Data
public class ThresholdConfig {
    /**
     * cpu 温度 高于当前温度将会告警提醒
     */
    private Double cpuTemperature;
    /**
     * CPU负载率 高于当前负载率将会提醒
     */
    private Double cpuLoad;
    /**
     * 可用内存百分比 低于将会告警提醒
     */
    private Double memoryAvailablePercent;
    /**
     * 可用磁盘量百分比，低于将会告警提醒
     */
    private Double diskAvailablePercent;
    /**
     * 电池可用百分比，低于将会告警提醒
     */
    private Double batteryRemainingPercent;

}
