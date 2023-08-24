package com.hbzb.tas.controller;


import com.hbzb.tas.dao.OpRecordRepo;
import com.hbzb.tas.entity.HandleRecord;
import com.hbzb.tas.entity.OpRecord;
import com.hbzb.tas.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * index common controller
 * created by dusizhong at 2020.01.04
 */
@Api(tags = "公用接口")
@RestController
public class IndexController {

    @ApiOperation(value = "greetings", httpMethod = "GET", response = String.class, position = 1)
    @RequestMapping("/")
    public String greetings(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();
        System.out.println(ipAddress);
        return "Greetings from tas server!";
    }

    @Autowired
    OpRecordRepo opRecordRepo;

    @ApiOperation(value = "获取处理历史列表", httpMethod = "GET", responseContainer = "LIST", response = HandleRecord.class, position = 2)
    @ApiImplicitParams({ @ApiImplicitParam(name = "itemUid", value = "所属项uid") })
    @GetMapping("/common/op_record/list")
    public List<OpRecord> getOpRecordList(@RequestParam(defaultValue = "") String itemUid) {
        return opRecordRepo.findByItemUid(itemUid.trim());
    }
}
