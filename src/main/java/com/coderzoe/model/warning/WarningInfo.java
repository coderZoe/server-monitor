package com.coderzoe.model.warning;

import lombok.*;

/**
 * @author coderZoe
 * @date 2023/8/14 20:17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarningInfo {
    private String name;
    private String content;

    @Override
    public String toString() {
        return "告警信息====" +
                "类型：'" + name + '\'' +
                ", 内容：'" + content + '\'' +
                '}';
    }
}
