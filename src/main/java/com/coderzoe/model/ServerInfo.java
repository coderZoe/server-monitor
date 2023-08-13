package com.coderzoe.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author coderZoe
 * @date 2023/8/13 19:35
 */
@Data
@Builder
public class ServerInfo {
    private OsInfo osInfo;
    private CpuInfo cpuInfo;
    private MemoryInfo memoryInfo;
    private DiskInfo diskInfo;
    private BatteryInfo batteryInfo;
}
