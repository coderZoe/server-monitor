package com.coderzoe.service;

import com.coderzoe.model.*;
import com.coderzoe.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author coderZoe
 * @date 2023/8/13 20:48
 */
@Service
@Slf4j
public class ServerMonitorService {

    public ServerInfo getServerInfo(){
        SystemInfo systemInfo = new SystemInfo();
        ServerInfo serverInfo = ServerInfo.builder()
                .osInfo(getOsInfo(systemInfo))
                .cpuInfo(getCpuInfo(systemInfo))
                .memoryInfo(getMemoryInfo(systemInfo))
                .diskInfo(getDiskInfo(systemInfo))
                .batteryInfo(getBatteryInfo(systemInfo))
                .build();
        log.info("生成监控信息：{}", JsonUtil.parseToJson(serverInfo));
        return serverInfo;
    }


    private OsInfo getOsInfo(SystemInfo systemInfo){
        OperatingSystem os = systemInfo.getOperatingSystem();
        return OsInfo.builder()
                .family(os.getFamily())
                .version(os.getVersionInfo().getVersion())
                .processCount(os.getProcessCount())
                .bootTime(new Date(os.getSystemBootTime()*1000L))
                .upTime(os.getSystemUptime()*1000L)
                .build();

    }

    private CpuInfo getCpuInfo(SystemInfo systemInfo){
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardware.getProcessor();
        Sensors sensors = hardware.getSensors();
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] nowTicks = centralProcessor.getSystemCpuLoadTicks();
        return CpuInfo.builder().physicalProcessorCount(centralProcessor.getPhysicalProcessorCount())
                .logicalProcessorCount(centralProcessor.getLogicalProcessorCount())
                .temperature(sensors.getCpuTemperature())
                .load(getCpuLoad(prevTicks,nowTicks))
                .systemLoad(getCpuSystemLoad(prevTicks,nowTicks))
                .userLoad(getCpuUserLoad(prevTicks,nowTicks))
                .ioWaitLoad(getCpuIoWaitLoad(prevTicks,nowTicks))
                .build();
    }


    private MemoryInfo getMemoryInfo(SystemInfo systemInfo){
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        return MemoryInfo.builder()
                .available(memory.getAvailable())
                .total(memory.getTotal())
                .build();
    }

    private DiskInfo getDiskInfo(SystemInfo systemInfo){
        List<OSFileStore> osFileStoreList = systemInfo.getOperatingSystem().getFileSystem().getFileStores();
        List<DiskInfo.Partition> partitionList = osFileStoreList.stream().map(osFileStore -> DiskInfo.Partition.builder()
                .name(osFileStore.getName())
                .totalSpace(osFileStore.getTotalSpace())
                .usableSpace(osFileStore.getUsableSpace())
                .usedSpace(osFileStore.getTotalSpace()-osFileStore.getUsableSpace())
                .build()).collect(Collectors.toList());
        return DiskInfo.builder()
                .partitionList(partitionList)
                .build();
    }

    private BatteryInfo getBatteryInfo(SystemInfo systemInfo){
        PowerSource powerSource = systemInfo.getHardware().getPowerSources().get(0);
        return BatteryInfo.builder()
                .remainingCapacity(powerSource.getRemainingCapacityPercent())
                .timeRemaining((long)(powerSource.getTimeRemainingEstimated() * 1000))
                .power(powerSource.getPowerUsageRate())
                .charging(powerSource.isCharging())
                .build();

    }


    private double getCpuLoad(long[] prevTicks,long[] nowTicks){
        long total = getTotal(prevTicks,nowTicks);
        // Calculate idle from difference in idle and io_wait
        long idle = nowTicks[CentralProcessor.TickType.IDLE.getIndex()] + nowTicks[CentralProcessor.TickType.IOWAIT.getIndex()]
                - prevTicks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];

        return total > 0 ? (double) (total - idle) / total : 0d;
    }

    private double getCpuSystemLoad(long[] prevTicks,long[] nowTicks){
        long user = nowTicks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long total = getTotal(prevTicks, nowTicks);
        return user*100.0/total;
    }
    private double getCpuUserLoad(long[] prevTicks,long[] nowTicks){
        long user = nowTicks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long total = getTotal(prevTicks, nowTicks);
        return user*100.0/total;
    }
    private double getCpuIoWaitLoad(long[] prevTicks,long[] nowTicks){
        long user = nowTicks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long total = getTotal(prevTicks, nowTicks);
        return user*100.0/total;
    }

    private long getTotal(long[] prevTicks,long[] nowTicks){
        long total = 0;
        for (int i = 0; i < nowTicks.length; i++) {
            total += nowTicks[i] - prevTicks[i];
        }
        return total;
    }
}
