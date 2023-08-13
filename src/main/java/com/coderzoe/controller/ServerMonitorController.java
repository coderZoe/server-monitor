package com.coderzoe.controller;

import com.coderzoe.model.ServerInfo;
import com.coderzoe.model.common.CommonReturn;
import com.coderzoe.service.ServerMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coderZoe
 * @date 2023/8/13 21:54
 */
@RestController
public class ServerMonitorController {
    private ServerMonitorService serverMonitorService;


    @GetMapping("/server")
    public CommonReturn<ServerInfo> getServerInfo(){
        return CommonReturn.success(serverMonitorService.getServerInfo());
    }


    @Autowired
    public void setServerMonitorService(ServerMonitorService serverMonitorService) {
        this.serverMonitorService = serverMonitorService;
    }
}
