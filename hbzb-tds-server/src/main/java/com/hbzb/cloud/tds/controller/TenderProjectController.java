package com.hbzb.cloud.tds.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "招标项目接口", description = "新增招标项目、更新招标项目、获取招标项目列表、获取招标项目详情")
@RestController
public class TenderProjectController {


    @RequestMapping("/tender_project/create")
    public void createTenderProject() {

    }

    @RequestMapping("/tender_project/update")
    public void updateTenderProject() {

    }

    @RequestMapping("/tender_project/update_status")
    public void updateProjectStatus() {

    }
}
