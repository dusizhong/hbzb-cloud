package com.hbzb.tas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "开发问题")
@RestController
public class ZquestionController {

    @ApiOperation(value = "1. 项目问题", notes = "  \n"

            + "1.1 新增项目中监督部门、审核部门是哪里？行政监督部门又是哪里？  \n"
            + "1.1 新增项目中投资项目统一代码是什么，哪里获取？  \n"
            + "1.1 新增项目时，代理机构名称可以随便填写？  \n"

            + "1.3 一个招标项目的分包是否可能有不同采购方式、和开标地点？是否可以将采购方式在分包中录入？  \n"

            + "2.1 采购预告什么时间发？单一来源？  \n"

            + "3.1 场地预约，开标一室、开标2室？按地区（邢台）？  \n"

            + "5.1 新增投标邀请为什么不用审核流程？邀请函中工本费和发售时间等信息不是在招标文件中已经录入了吗？招标人和招标代理信息为什么也需要新录入？  \n"
    )
    @GetMapping("/zquestion/no1")
    public void no1() {

    }
}
