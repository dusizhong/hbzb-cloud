package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.BidFileRepo;
import com.hbzb.tas.dao.TenderSectionRepo;
import com.hbzb.tas.entity.BidFile;
import com.hbzb.tas.entity.TenderSection;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 投标文件接口
 * created by dsz at 2020.05.22
 */
@RestController
public class BidFileController {

    @Autowired
    UaaService uaaService;
    @Autowired
    TenderSectionRepo tenderSectionRepo;
    @Autowired
    BidFileRepo bidFileRepo;

    @RequestMapping("/bid/file/list")
    public Resp getBidFileList(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 检查标段
        TenderSection tenderSection = tenderSectionRepo.findBySectionUid(sectionUid.trim());
        if(tenderSection == null) return new Resp("400", "无此标段");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        if(!agencyUid.equals(tenderSection.getAgencyUid())) return new Resp("400", "无权操作其他代理标段");
        // 获取数据
        List<BidFile> bidFiles = bidFileRepo.findBySectionUid(sectionUid.trim());
        return new Resp("200", "获取成功", bidFiles);
    }
}
