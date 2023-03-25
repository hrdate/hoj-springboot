package top.hcode.hoj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hcode.hoj.service.SystemConfigService;

import java.util.HashMap;

/**
 * @Description:
 */
@RestController
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping("/get-sys-config")
    public HashMap<String,Object> getSystemConfig(){
        return systemConfigService.getSystemConfig();
    }
}