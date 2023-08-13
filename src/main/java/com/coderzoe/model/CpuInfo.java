package com.coderzoe.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author coderZoe
 * @date 2023/8/13 19:37
 */
@Data
@Builder
public class CpuInfo {
    /**
     * 物理处理器个数
     */
    private Integer physicalProcessorCount;
    /**
     * 逻辑处理器个数
     */
    private Integer logicalProcessorCount;
    /**
     * cpu 温度
     */
    private Double temperature;
    /**
     * cpu 当前负载
     */
    private Double load;
    /**
     * cpu 系统负载
     */
    private Double systemLoad;
    /**
     * cpu 用户负载
     */
    private Double userLoad;
    /**
     * cpu io_wait负载
     */
    private Double ioWaitLoad;

}
