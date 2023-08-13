package com.coderzoe.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 操作系统信息
 * @author coderZoe
 * @date 2023/8/13 19:51
 */
@Data
@Builder
public class OsInfo {
    /**
     * 操作系统家族
     */
    private String family;
    /**
     * 操作系统版本
     */
    private String version;
    /**
     * 当前操作系统运行的进程个数
     */
    private Integer processCount;
    /**
     * 系统已经运行时间 单位 ms
     */
    private long upTime;
    /**
     * 系统启动时间
     */
    private Date bootTime;
}
