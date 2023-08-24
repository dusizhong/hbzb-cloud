package com.hbzb.bis.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.bis.dao.BidRecordRepo;
import com.hbzb.bis.entity.BidRecord;
import com.hbzb.bis.model.Resp;
import com.hbzb.bis.service.UaaService;
import com.hbzb.bis.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 投标接口
 */
@RestController
public class BidRecordController {

    @Autowired
    UaaService uaaService;
//    @Autowired
//    SectionRepo sectionRepo;
    @Autowired
    BidRecordRepo bidRecordRepo;


    @RequestMapping("/bid/record/list")
    public Resp getBidList(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // 检查标段
//        Section section = sectionRepo.findByUid(sectionUid.trim());
//        if(section == null) return new Resp("400", "无此标段");
//        if(!agencyUid.equals(section.getAgencyUid())) return new Resp("400", "无权查看其他代理招标标段信息");
        // 筛选数据
        List<BidRecord> bidRecords = bidRecordRepo.findBySectionUid(sectionUid.trim());

        return new Resp("200", "获取成功", bidRecords);
    }
}
