package com.coderzoe.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author coderZoe
 * @date 2023/8/13 20:10
 */
@Data
@Builder
public class DiskInfo {
    private List<Partition> partitionList;


    /**
     * 一个分区的信息
     */
    @Data
    @Builder
    public static class Partition{
        /**
         * 分区名
         */
        private String name;
        /**
         * 总大小
         */
        private Long totalSpace;
        /**
         * 已使用大小
         */
        private Long usedSpace;
        /**
         * 可使用大小
         */
        private Long usableSpace;
    }
}
