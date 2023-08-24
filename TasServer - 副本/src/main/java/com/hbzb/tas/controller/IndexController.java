package com.hbzb.tas.controller;


import com.hbzb.tas.dao.OpRecordRepo;
import com.hbzb.tas.entity.HandleRecord;
import com.hbzb.tas.entity.OpRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * index common controller
 * created by dusizhong at 2020.01.04
 */
@Api(tags = "公用接口")
@RestController
public class IndexController {

    @ApiOperation(value = "greetings", httpMethod = "GET", response = String.class, position = 1)
    @GetMapping("/")
    public String greetings() {
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
