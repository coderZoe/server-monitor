package com.coderzoe.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author coderZoe
 * @date 2023/8/13 20:10
 */
@Data
@Builder
public class BatteryInfo {
    /**
     * 电源剩余电量 按百分比
     */
    private Double remainingCapacity;
    /**
     * 这些电量预计能用多久 单位ms
     */
    private Long timeRemaining;
    /**
     * 电池的功率使用率，以毫瓦 (mW) 为单位。
     * 如果为正，则充电率。如果为负，则为放电率
     */
    private Double power;
    /**
     * 是否正在充电
     */
    private Boolean charging;
}
