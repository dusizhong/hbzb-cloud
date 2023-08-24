package com.hbzb.tas.controller;


import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.dao.TenderSectionRepo;
import com.hbzb.tas.dao.TenderSheetRepo;
import com.hbzb.tas.entity.Section;
import com.hbzb.tas.entity.TenderSection;
import com.hbzb.tas.entity.TenderSheet;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TenderSheetController {

    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    TenderSheetRepo tenderSheetRepo;

    @RequestMapping("/tender/sheet/create")
    public Resp createTenderSheet(@RequestParam(defaultValue = "") String sectionUid,
                                  @RequestParam(defaultValue = "") String item,
                                  @RequestParam(defaultValue = "") String type,
                                  @RequestParam(defaultValue = "") String unit,
                                  @RequestParam(defaultValue = "") String memo) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(item)) return new Resp("400", "唱标项不能为空");
        if(StringUtils.isEmpty(type)) return new Resp("400", "数据类型不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 新增/更新
        TenderSheet tenderSheet = new TenderSheet();
        tenderSheet.setSectionUid(sectionUid.trim());
        tenderSheet.setItem(item.trim());
        tenderSheet.setType(type.trim());
        tenderSheet.setUnit(unit.trim());
        tenderSheet.setMemo(memo.trim());
        tenderSheet.setCreatorUid(creatorUid);
        tenderSheet.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderSheet.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderSheet = tenderSheetRepo.saveAndFlush(tenderSheet);
        return new Resp("200", "新增成功", tenderSheet);
    }


    @RequestMapping("/tender/sheet/list")
    public Resp getTenderSheetList(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取数据
        List<TenderSheet> tenderSheets = tenderSheetRepo.findBySectionUid(sectionUid.trim());
        return new Resp("200", "获取成功", tenderSheets);
    }


    @RequestMapping("/tender/sheet/del")
    public Resp delTenderSheet(@RequestParam(defaultValue = "") String id) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(id)) return new Resp("400", "唱标项id不能为空");
        if(!StringUtils.isInt(id)) return new Resp("400", "唱标项id格式错误");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 获取数据
        TenderSheet tenderSheet = tenderSheetRepo.findOne(Integer.valueOf(id.trim()));
        if(tenderSheet == null) return new Resp("400", "无此唱标项");
        // 检查标段
        Section section = sectionRepo.findByUid(tenderSheet.getSectionUid());
        if(section == null) return new Resp("400", "数据异常！该唱标项招标信息不存在");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        if(!creatorUid.equals(tenderSheet.getCreatorUid())) return new Resp("400", "此唱标项非你创建，无权删除");
        // 删除
        tenderSheetRepo.delete(tenderSheet);
        return new Resp("200", "删除成功", tenderSheet);
    }
}
