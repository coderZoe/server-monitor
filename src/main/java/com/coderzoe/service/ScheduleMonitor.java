package com.coderzoe.service;

import com.coderzoe.config.ThresholdConfig;
import com.coderzoe.model.DiskInfo;
import com.coderzoe.model.ServerInfo;
import com.coderzoe.model.warning.WarningInfo;
import com.coderzoe.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时监控
 * @author coderZoe
 * @date 2023/8/14 11:27
 */
@Service
@Slf4j
public class ScheduleMonitor {
    private ServerMonitorService serverMonitorService;
    private ThresholdConfig thresholdConfig;
    private EmailService emailService;

    @Value("${spring.mail.to-email}")
    private String[] toEmail;
    private MailProperties mailProperties;

    private static final String[] UNIT = {"Byte","KB","MB","GB","TB","PB"};
    private static final String SUBJECT = "服务器监控告警";
    @Scheduled(fixedRate = 1000*60)
    public void schedule(){
        ServerInfo serverInfo = serverMonitorService.getServerInfo();
        List<WarningInfo> warningInfoList = this.getWarningInfo(serverInfo);
        if(!CollectionUtils.isEmpty(warningInfoList)){
            log.warn("生成告警信息：{}，准备发送到邮件", JsonUtil.parseToJson(warningInfoList));
            emailService.sendMail(mailProperties.getUsername(),toEmail,SUBJECT,textShow(warningInfoList));
        }
    }

    public List<WarningInfo> getWarningInfo(ServerInfo serverInfo){
        List<WarningInfo> warningInfoList = new ArrayList<>();
        if (thresholdConfig.getCpuTemperature() != null
                && serverInfo.getCpuInfo().getTemperature() > thresholdConfig.getCpuTemperature()) {
            warningInfoList.add(WarningInfo.builder()
                    .name("CPU温度")
                    .content("CPU温度过高！！！当前温度：" + String.format("%.2f",serverInfo.getCpuInfo().getTemperature())+"°C")
                    .build());
        }
        if (thresholdConfig.getCpuLoad() != null
                && serverInfo.getCpuInfo().getLoad() > thresholdConfig.getCpuLoad()) {
            warningInfoList.add(WarningInfo.builder()
                    .name("CPU负载")
                    .content("CPU负载过高！！！当前负载：" + percentShow(serverInfo.getCpuInfo().getLoad()))
                    .build());
        }

        if (thresholdConfig.getMemoryAvailablePercent() != null) {
            double percent = (double) serverInfo.getMemoryInfo().getAvailable() / (double) serverInfo.getMemoryInfo().getTotal();
            if(percent < thresholdConfig.getMemoryAvailablePercent()){
                warningInfoList.add(WarningInfo.builder()
                        .name("可用内存")
                        .content("可用内存过低！！！当前可用内存：" + byteShow(serverInfo.getMemoryInfo().getAvailable(),0)
                        +",可用内存比例："+percentShow(percent))
                        .build());
            }
        }
        if (thresholdConfig.getDiskAvailablePercent() != null) {
            for (DiskInfo.Partition partition : serverInfo.getDiskInfo().getPartitionList()) {
                double percent = (double) partition.getUsableSpace() / (double) partition.getTotalSpace();
                if(percent < thresholdConfig.getDiskAvailablePercent()){
                    warningInfoList.add(WarningInfo.builder()
                            .name(partition.getName())
                            .content("可用磁盘过低！！！当前可用磁盘：" + byteShow(partition.getUsableSpace(), 0)
                                    +",可用磁盘比例："+percentShow(percent))
                            .build());
                }
            }
        }

        if (thresholdConfig.getBatteryRemainingPercent() != null && serverInfo.getBatteryInfo().getRemainingCapacity() < thresholdConfig.getBatteryRemainingPercent()) {
            warningInfoList.add(WarningInfo.builder()
                    .name("电量过低")
                    .content("电量过低！！！当前电量剩余：" + percentShow(serverInfo.getBatteryInfo().getRemainingCapacity()))
                    .build());
        }
        return warningInfoList;
    }

    private String byteShow(long byteCount,int times){
        long temp = byteCount / 1024L;
        if(temp < 1){
            return byteCount+UNIT[times];
        }else {
            return byteShow(temp,++times);
        }
    }

    private String percentShow(double percent){
        return String.format("%.2f",percent*100) +"%";
    }

    private String textShow(List<WarningInfo> warningInfoList){
        StringBuilder stringBuilder = new StringBuilder();
        for(WarningInfo warningInfo:warningInfoList){
            stringBuilder.append(warningInfo.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Autowired
    public void setServerMonitorService(ServerMonitorService serverMonitorService) {
        this.serverMonitorService = serverMonitorService;
    }
    @Autowired
    public void setThresholdConfig(ThresholdConfig thresholdConfig) {
        this.thresholdConfig = thresholdConfig;
    }
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    @Autowired
    public void setMailProperties(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }
}
