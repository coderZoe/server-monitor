package com.coderzoe.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阈值配置，当服务器指标阈值
 * @author coderZoe
 * @date 2023/8/14 11:36
 */
@Configurable
@ConfigurationProperties("")
public class ThresholdConfig {
    private double cpuTemperatureMax;
    private double cpuLoadMax;
    private long memoryAvailable;
    private long diskAvailable;
    private Double batteryRemainingPercent;

}
