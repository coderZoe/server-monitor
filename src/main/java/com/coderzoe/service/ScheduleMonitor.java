package com.coderzoe.service;

import com.coderzoe.model.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时监控
 * @author coderZoe
 * @date 2023/8/14 11:27
 */
@Service
public class ScheduleMonitor {
    private ServerMonitorService serverMonitorService;
    private EmailService emailService;
    @Scheduled
    public void schedule(){
        ServerInfo serverInfo = serverMonitorService.getServerInfo();
    }
    @Autowired
    public void setServerMonitorService(ServerMonitorService serverMonitorService) {
        this.serverMonitorService = serverMonitorService;
    }
    @Autowired
    public void setSendMailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
