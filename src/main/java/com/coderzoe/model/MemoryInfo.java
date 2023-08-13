package com.coderzoe.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author coderZoe
 * @date 2023/8/13 19:38
 */
@Data
@Builder
public class MemoryInfo {
    /**
     * 总共的内存大小
     */
    private Long total;
    /**
     * 可用内存大小
     */
    private Long available;
}
