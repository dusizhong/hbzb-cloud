package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.dao.TenderBidFileRepo;
import com.hbzb.tas.dao.TenderSectionRepo;
import com.hbzb.tas.entity.Section;
import com.hbzb.tas.entity.TenderBidFile;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 投标文件组成设置接口
 */
@RestController
public class TenderBidFileController {

    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    TenderBidFileRepo tenderBidFileRepo;


    @RequestMapping("/tender/bidfile/create")
    public Resp createTenderBidFile(@RequestParam(defaultValue = "") String id,
                                    @RequestParam(defaultValue = "") String sortId,
                                    @RequestParam(defaultValue = "") String sectionUid,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String note,
                                    @RequestParam(defaultValue = "") String needed,
                                    @RequestParam(defaultValue = "") String sealed) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(sortId)) return new Resp("400", "序号不能为空");
        if(!StringUtils.isInt(sortId)) return new Resp("400", "序号格式有误");
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(name)) return new Resp("400", "文件名称不能为空");
        if(StringUtils.isEmpty(needed)) return new Resp("400", "是否必须不能为空");
        if(StringUtils.isEmpty(sealed)) return new Resp("400", "是否签章不能为空");
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
        TenderBidFile tenderBidFile;
        if(StringUtils.isEmpty(id)) {
            tenderBidFile = new TenderBidFile();
            tenderBidFile.setSectionUid(sectionUid.trim());
            tenderBidFile.setCreatorUid(creatorUid);
            tenderBidFile.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            if(!StringUtils.isInt(id)) return new Resp("400", "id格式有误");
            tenderBidFile = tenderBidFileRepo.findOne(Integer.valueOf(id.trim()));
            if(tenderBidFile == null) return new Resp("400", "无此招标文件组成项");
            if(!creatorUid.equals(tenderBidFile.getCreatorUid()))  return new Resp("400", "此招标文件组成项非你创建，无权修改");
        }
        tenderBidFile.setSortId(Integer.valueOf(sortId.trim()));
        tenderBidFile.setName(name.trim());
        tenderBidFile.setNote(note.trim());
        tenderBidFile.setNeeded(needed.trim());
        tenderBidFile.setSealed(sealed.trim());
        tenderBidFile.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderBidFile = tenderBidFileRepo.saveAndFlush(tenderBidFile);
        return new Resp("200", "操作成功", tenderBidFile);
    }


    @RequestMapping("/tender/bidfile/list")
    public Resp getTenderBidFileList(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取数据
        List<TenderBidFile> tenderBidFiles = tenderBidFileRepo.findBySectionUidOrderBySortId(sectionUid.trim());
        return new Resp("200", "获取成功", tenderBidFiles);
    }


    @RequestMapping("/tender/bidfile/del")
    public Resp delTenderBidFile(@RequestParam(defaultValue = "") String id) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(id)) return new Resp("400", "投标文件组成项id不能为空");
        if(!StringUtils.isInt(id)) return new Resp("400", "id格式错误");
        // 获取数据
        TenderBidFile tenderBidFile = tenderBidFileRepo.findOne(Integer.valueOf(id.trim()));
        if(tenderBidFile == null) return new Resp("400", "无此唱标项");
        // 检查标段
        Section section = sectionRepo.findByUid(tenderBidFile.getSectionUid());
        if(section == null) return new Resp("400", "数据异常！该投标文件组成项所属招标信息不存在");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态状态不在编辑中");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(!creatorUid.equals(tenderBidFile.getCreatorUid())) return new Resp("400", "此投标文件组成项非你创建，无权删除");
        // 删除
        tenderBidFileRepo.delete(tenderBidFile);
        return new Resp("200", "删除成功", tenderBidFile);
    }
}
