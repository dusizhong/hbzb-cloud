package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.ProjectRepo;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.entity.Project;
import com.hbzb.tas.entity.Section;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.OpRecordService;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 标段接口
 * created by dusizhong at 2020.02.03
 */
@Api(tags = "标段接口")
@RestController
public class SectionController {

    @Autowired
    UaaService uaaService;
    @Autowired
    OpRecordService opRecordService;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    SectionRepo sectionRepo;


//    @ApiOperation(value = "新增/更新标段", httpMethod = "POST", response = Section.class, position = 1)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "projectUid", value = "项目uid", required = true),
//            @ApiImplicitParam(name = "uid", value = "标段uid"),
//            @ApiImplicitParam(name = "serialNo", value = "标段编号", required = true),
//            @ApiImplicitParam(name = "name", value = "标段名称", required = true),
//            @ApiImplicitParam(name = "price", value = "单价（元）"),
//            @ApiImplicitParam(name = "num", value = "采购数量"),
//            @ApiImplicitParam(name = "budget", value = "预算总价（元）"),
//            @ApiImplicitParam(name = "unit", value = "型号单位"),
//            @ApiImplicitParam(name = "content", value = "标段内容"),
//            @ApiImplicitParam(name = "memo", value = "备注"),
//            @ApiImplicitParam(name = "approverUid", value = "审批人uid")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "操作成功"),
//            @ApiResponse(code = 400, message = "无权访问|参数不能为空|")})
    @RequestMapping("/section/create")
    public Resp createSection(@RequestParam(defaultValue = "") String projectUid,
                              @RequestParam(defaultValue = "") String uid,
                              @RequestParam(defaultValue = "") String serialNo,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String area,
                              @RequestParam(defaultValue = "") String tradeType,
                              @RequestParam(defaultValue = "") String tradeCategory,
                              @RequestParam(defaultValue = "") String budget,
                              @RequestParam(defaultValue = "") String bidOpenTime,
                              @RequestParam(defaultValue = "") String bidOpenPlace,
                              @RequestParam(defaultValue = "") String bidOnline,
                              @RequestParam(defaultValue = "") String needGuarantee,
                              @RequestParam(defaultValue = "") String content,
                              @RequestParam(defaultValue = "") String qualification,
                              @RequestParam(defaultValue = "") String memo,
                              @RequestParam(defaultValue = "") String approverUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        if(StringUtils.isEmpty(serialNo)) return new Resp("400", "标段编号不能为空");
        if(serialNo.trim().length() > 23) return new Resp("400", "标段编号不能超过23位");
        if(StringUtils.isEmpty(name)) return new Resp("400", "标段名称不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String createrName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 获取代理信息
        JSONObject jsonCorp = uaaService.requestCorpDetail();
        if(jsonCorp == null) return new Resp("400", "UAA: 获取招标代理信息失败");
        if(!"200".equals(jsonCorp.get("code"))) return new Resp("400", "UAA: " + jsonCorp.getString("message"));
        JSONObject agency = (JSONObject) JSONObject.toJSON(jsonCorp.get("data"));
        String agencyUid = agency.getString("uid");
        String agencyName = agency.getString("name");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取招标代理uid失败");
        if(StringUtils.isEmpty(agencyName)) return new Resp("400", "UAA: 获取招标代理名称失败");
        // 检查项目
        Project project = projectRepo.findByUid(projectUid.trim());
        if(project == null) return new Resp("400", "无此项目");
        if(!"APPROVAL".equals(project.getStatus())) return new Resp("400", "项目状态不符");
        if(!creatorUid.equals(project.getCreatorUid())) return new Resp("400", "此项目非你创建，无权操作");
        // 新增/更新标段
        Section section;
        if(StringUtils.isEmpty(uid)) {
            Section existSerialNo = sectionRepo.findBySerialNo(serialNo.trim());
            if(existSerialNo != null) return new Resp("400", "标段编号已存在");
            // 注：标段名称仅在同一项目中唯一
            Section existName = sectionRepo.findByProjectUidAndName(projectUid.trim(), name.trim());
            if(existName != null) return new Resp("400", "标段名称已存在");
            section = new Section();
            section.setProjectUid(projectUid.trim());
            section.setUid(UUID.randomUUID().toString().replace("-", ""));
            section.setStatus("EDIT");
            section.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            section = sectionRepo.findByUid(uid.trim());
            if(section == null) return new Resp("400", "标段不存在");
            Section existSerialNo = sectionRepo.findBySerialNo(serialNo.trim());
            if(existSerialNo != null && !existSerialNo.getSerialNo().equals(section.getSerialNo())) return new Resp("400", "标段编号已存在");
            // 注：标段（包）名称仅在同一项目中唯一
            Section existName = sectionRepo.findByProjectUidAndName(projectUid.trim(), name.trim());
            if(existName != null && !existName.getName().equals(section.getName())) return new Resp("400", "标段名称已存在");
        }
        section.setSerialNo(serialNo.trim());
        section.setName(name.trim());
        section.setArea(area.trim());
        section.setTradeType(tradeType.trim());
        section.setTradeCategory(tradeCategory.trim());
        section.setBudget(budget.trim());
        section.setBidOpenTime(bidOpenTime.trim());
        section.setBidOpenPlace(bidOpenPlace.trim());
        section.setBidOnline(bidOnline.trim());
        section.setNeedGuarantee(needGuarantee.trim());
        section.setContent(content.trim());
        section.setQualification(qualification.trim());
        section.setMemo(memo.trim());
        section.setKeyword(serialNo + " " + name);
        section.setAgencyUid(agencyUid);
        section.setAgencyName(agencyName);
        section.setApproverUid(approverUid.trim());
        section.setCreatorUid(creatorUid);
        section.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        section = sectionRepo.save(section);
        return new Resp("200", "操作成功", section);
    }


    @ApiOperation(value = "获取标段列表", notes = "获取标段列表",
            httpMethod = "GET", responseContainer = "LIST", response = Section.class, position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid"),
            @ApiImplicitParam(name = "keyword", value = "搜索关键字"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页记录数"),
            @ApiImplicitParam(name = "sort", value = "排序值（默认：id,DESC）")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符|权限不足")})
    @RequestMapping("/section/list")
    public Resp getSectionList(@RequestParam(defaultValue = "") String projectUid,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String status,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer size,
                               @RequestParam(defaultValue = "id,DESC") String sort) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        Sort mySort;
        String[] sorts = sort.split(",");
        if(sorts.length < 2) return new Resp("400", "排序参数格式错误");
        if(sorts[1].equals("ASC")) mySort = new Sort(Sort.Direction.ASC, sorts[0]);
        else if(sorts[1].equals("DESC")) mySort = new Sort(Sort.Direction.DESC, sorts[0]);
        else return new Resp("400", "排序参数无效");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // 筛选数据
        List<Section> sections = sectionRepo.findByAgencyUid(agencyUid, mySort);
        if(!StringUtils.isEmpty(projectUid)) sections = sections.stream().filter(s -> s.getProjectUid().equals(projectUid.trim())).collect(Collectors.toList());
        if(!StringUtils.isEmpty(keyword)) sections = sections.stream().filter(s -> s.getKeyword().contains(keyword.trim())).collect(Collectors.toList());
        if(!StringUtils.isEmpty(status)) sections = sections.stream().filter(s -> s.getStatus().equals(status.trim())).collect(Collectors.toList());
        // 处理分页
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>sections.size()?sections.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>sections.size()? sections.size():(start + pageable.getPageSize());
        return new Resp("200", "获取成功", new PageImpl<>(sections.subList(start,end), pageable, sections.size()));
    }

    /**
     * 获取多条标段信息
     * 用于公告详情
     * @param sectionUids
     * @return
     */
    @RequestMapping("/section/list2")
    public Resp getSectionList2(@RequestParam(defaultValue = "") String sectionUids) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUids)) return new Resp("400", "标段uids不能为空");
        List<String> uids = Arrays.asList(sectionUids.split(","));
        if(uids.size() < 1) return new Resp("400", "标段uids格式无效");
        // 获取数据
        List<Section> sections = sectionRepo.findByUidIn(uids);
        return new Resp("200", "获取成功", sections);
    }


    @ApiOperation(value = "获取标段详情", notes = "获取标段详情", httpMethod = "GET", response = Section.class, position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionUid", value = "标段uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数不能为空|无此标段")})
    @RequestMapping("/section/detail")
    public Resp getSectionDetail(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        return new Resp("200", "获取成功", section);
    }


    @ApiOperation(value = "删除标段", notes = "删除编辑中的标段，仅限创建人。",
            httpMethod = "POST", response = Resp.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionUid", value = "标段uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此标段|无权删除他人的标段")})
    @RequestMapping("/section/del")
    public Resp delSection(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段（包）uid不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 获取标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中，不能删除");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "无权删除他人的标段");
        sectionRepo.delete(section);
        // 更新操作记录
        opRecordService.createRecord("标段", section.getUid(), "删除标段（包）", section.getName(), creatorUid, creatorName);
        return new Resp("200", "删除成功");
    }
}
