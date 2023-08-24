package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.ProjectMaterialRepo;
import com.hbzb.tas.dao.ProjectRepo;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.entity.Project;
import com.hbzb.tas.entity.ProjectMaterial;
import com.hbzb.tas.entity.Section;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.OpRecordService;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * 项目接口
 * created by dusizhong at 2020.01.14
 */
@Api(tags = "1.1 项目接口")
@RestController
public class ProjectController {

    @Autowired
    UaaService uaaService;
    @Autowired
    OpRecordService opRecordService;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    ProjectMaterialRepo projectMaterialRepo;
    @Autowired
    SectionRepo sectionRepo;


    @ApiOperation(value = "新增/更新项目", httpMethod = "POST", response = Project.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "项目uid"),
            @ApiImplicitParam(name = "serialNo", value = "项目编号", required = true),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true),
            @ApiImplicitParam(name = "tendereeName", value = "采购单位", required = true),
            @ApiImplicitParam(name = "contactPerson", value = "采购联系人", required = true),
            @ApiImplicitParam(name = "contactPhone", value = "采购联系电话", required = true),
            @ApiImplicitParam(name = "address", value = "采购地址", required = true),
            @ApiImplicitParam(name = "area", value = "所在辖区", required = true),
            @ApiImplicitParam(name = "tradeType", value = "采购方式（公开招标、邀请招标、竞争性谈判、竞争性磋商、询价、单一来源）", required = true),
            @ApiImplicitParam(name = "tradeCategory", value = "采购交易分类（货物类（含药品集中采购）、工程类、服务类）"),
            @ApiImplicitParam(name = "organizeType", value = "交易/招标组织形式（1自主招标（自行招标）、2社会代理（委托招标）、采购中心、9其他）"),
            @ApiImplicitParam(name = "investmentCode", value = "投资项目统一代码（代码长度：24位，代码格式：年份代码-地区（部门）代码-行业代码-项目类型代码-流水号）"),
            @ApiImplicitParam(name = "investorType", value = "投资主体性质（政府投资、企业投资、其他）"),
            @ApiImplicitParam(name = "isPPP", value = "是否PPP项目"),
            @ApiImplicitParam(name = "supervisor", value = "行政监督部门"),
            @ApiImplicitParam(name = "resourcer", value = "公共资源"),
            @ApiImplicitParam(name = "platform", value = "推送平台"),
            @ApiImplicitParam(name = "content", value = "采购内容与范围及招标方案说明"),
            @ApiImplicitParam(name = "approverUid", value = "审核人uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增/更新成功"),
            @ApiResponse(code = 400, message = "无权访问|参数不能为空|")})
    @RequestMapping("/project/create")
    public Resp createProject(@RequestParam(defaultValue = "") String uid,
                                    @RequestParam(defaultValue = "") String serialNo,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String tendereeName,
                                    @RequestParam(defaultValue = "") String contactPerson,
                                    @RequestParam(defaultValue = "") String contactPhone,
                                    @RequestParam(defaultValue = "") String address,
                                    @RequestParam(defaultValue = "") String area,
                                    @RequestParam(defaultValue = "") String tradeType,
                                    @RequestParam(defaultValue = "") String tradeCategory,
                                    @RequestParam(defaultValue = "") String organizeType,
                                    @RequestParam(defaultValue = "") String investmentCode,
                                    @RequestParam(defaultValue = "") String investorType,
                                    @RequestParam(defaultValue = "") String isPPP,
                                    @RequestParam(defaultValue = "") String supervisor,
                                    @RequestParam(defaultValue = "") String resourcer,
                                    @RequestParam(defaultValue = "") String platform,
                                    @RequestParam(defaultValue = "") String content,
                                    @RequestParam(defaultValue = "") String approverUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(serialNo)) return new Resp("400", "项目编号不能为空");
        if(serialNo.trim().length() > 20) return new Resp("400", "项目编号不能超过20位");
        if(StringUtils.isEmpty(name)) return new Resp("400", "项目名称不能为空");
        if(StringUtils.isEmpty(tendereeName)) return new Resp("400", "采购单位不能为空");
        if(StringUtils.isEmpty(area)) return new Resp("400", "所在辖区不能为空");
        if(StringUtils.isEmpty(tradeType)) return new Resp("400", "采购方式不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "获取用户uid失败");
        // 获取代理信息
        JSONObject jsonCorp = uaaService.requestCorpDetail();
        if(jsonCorp == null) return new Resp("400", "UAA: 获取招标代理信息失败");
        if(!"200".equals(jsonCorp.get("code"))) return new Resp("400", "UAA: " + jsonCorp.getString("message"));
        JSONObject agency = (JSONObject) JSONObject.toJSON(jsonCorp.get("data"));
        String agencyUid = agency.getString("uid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取招标代理uid失败");
        String agencyName = agency.getString("name");
        if(StringUtils.isEmpty(agencyName)) return new Resp("400", "UAA: 获取招标代理名称失败");
        // 新增/更新项目
        Project project;
        if(StringUtils.isEmpty(uid)) {
            Project existSerialNo = projectRepo.findBySerialNo(serialNo.trim());
            if(existSerialNo != null) return new Resp("400", "项目编号已存在");
            Project existName = projectRepo.findByName(name.trim());
            if(existName != null) return new Resp("400", "项目名称已存在");
            project = new Project();
            project.setUid(UUID.randomUUID().toString().replace("-", ""));
            project.setAgencyUid(agencyUid);
            project.setAgencyName(agencyName);
            project.setStatus("EDIT");
            project.setCreatorUid(creatorUid);
            project.setCreatorName(creatorName);
            project.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            project = projectRepo.findByUid(uid.trim());
            if(project == null) return new Resp("400", "项目不存在");
            if(!"EDIT".equals(project.getStatus())) return new Resp("400", "项目状态不在编辑中，不能更新");
            if(!creatorUid.equals(project.getCreatorUid())) return new Resp("400", "此项目非你创建，无权操作");
            Project existSerialNo = projectRepo.findBySerialNo(serialNo.trim());
            if(existSerialNo != null && !existSerialNo.getSerialNo().equals(project.getSerialNo())) return new Resp("400", "项目编号已存在");
            Project existName = projectRepo.findByName(name.trim());
            if(existName != null && !existName.getName().equals(project.getName())) return new Resp("400", "项目名称已存在");
        }
        project.setSerialNo(serialNo.trim());
        project.setName(name.trim());
        project.setTendereeName(tendereeName.trim());
        project.setContactPerson(contactPerson.trim());
        project.setContactPhone(contactPhone.trim());
        project.setAddress(address.trim());
        project.setArea(area.trim());
        project.setTradeType(tradeType.trim());
        project.setTradeCategory(tradeCategory.trim());
        project.setOrganizeType(organizeType.trim());
        project.setInvestmentCode(investmentCode.trim());
        project.setInvestorType(investorType.trim());
        project.setIsPPP(isPPP.trim());
        project.setSupervisor(supervisor.trim());
        project.setResourcer(resourcer.trim());
        project.setPlatform(platform.trim());
        project.setContent(content.trim());
        project.setApproverUid(approverUid.trim());
        project.setKeyword(serialNo + " " + name + " " + tendereeName + " " + agencyName + " " + area + " " + tradeType
                + " " + tradeCategory + " " + organizeType);
        project.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        project = projectRepo.saveAndFlush(project);
        return new Resp("200", "操作成功", project);
    }


    @ApiOperation(value = "更新项目状态", httpMethod = "POST", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid", required = true),
            @ApiImplicitParam(name = "status", value = "操作类型(EDIT编辑中、SUBMIT提交审核、REJECT审核不通过、APPROVAL审核通过、TRANSFER转审核)", required = true),
            @ApiImplicitParam(name = "memo", value = "备注"),
            @ApiImplicitParam(name = "approverUid", value = "审核人uid（提交审核必填）")
    })
    @RequestMapping("/project/status/update")
    public Resp updateProjectStatus(@RequestParam(defaultValue = "") String projectUid,
                                    @RequestParam(defaultValue = "") String status,
                                    @RequestParam(defaultValue = "") String memo,
                                    @RequestParam(defaultValue = "") String approvalUid) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        if(StringUtils.isEmpty(status)) return new Resp("400", "状态不能为空");
        if(!status.equals("EDIT") && !status.equals("SUBMIT") && !status.equals("TRANSFER")
                && !status.equals("REJECT") && !status.equals("APPROVAL")) return new Resp("400", "状态值无效");
        // check project
        Project project = projectRepo.findByUid(projectUid.trim());
        if(project == null) return new Resp("400", "无此项目");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if("EDIT".equals(status)) {
            if(!"REJECT".equals(status)) return new Resp("400", "项目状态不是已退回，不能操作");
            if(!creatorUid.equals(project.getCreatorUid())) return new Resp("400", "无权提交他人项目");
        } else if("SUBMIT".equals(status)) {
            if(!"EDIT".equals(project.getStatus())) return new Resp("400", "项目状态不在编辑中");
            if(!creatorUid.equals(project.getCreatorUid())) return new Resp("400", "无权提交他人项目");
        } else if("REJECT".equals(status) || "APPROVAL".equals(status)) {
//            if (!"SUBMIT".equals(project.getStatus())) return new Resp("400", "项目状态不是已提交");
//            if (!creatorUid.equals(project.getApproverUid())) return new Resp("400", "审核人不符，无权操作");
        } else if("TRANSFER".equals(status)) { //转审核
            if(StringUtils.isEmpty(approvalUid)) return new Resp("400", "审核人uid不能为空");
        }
        project.setStatus(status);
        project.setMemo(memo.trim());
        project.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        projectRepo.save(project);
        // update project section status
        List<Section> sections = sectionRepo.findByProjectUid(project.getUid());
        for(Section section : sections) {
            section.setStatus(status);
        }
        sectionRepo.save(sections);
        // create operate record
        opRecordService.createRecord("项目", project.getUid(), status, project.getName(), creatorUid, creatorName);
        return new Resp("200", "操作成功");
    }


    @ApiOperation(value = "获取项目列表", notes = "获取项目列表。",
            httpMethod = "GET", response = Page.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页记录数"),
            @ApiImplicitParam(name = "sort", value = "排序值（默认：id,DESC）")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符")})
    @RequestMapping("/project/list")
    public Resp getProjectList(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String status,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer size,
                               @RequestParam(defaultValue = "id,DESC") String sort) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
//        if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp("400", "权限不足");
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
        List<Project> projectList = projectRepo.findByAgencyUid(agencyUid, mySort);
        if(!StringUtils.isEmpty(keyword)) projectList = projectList.stream().filter(t->t.getKeyword().contains(keyword)).collect(Collectors.toList());
        if(!StringUtils.isEmpty(status)) projectList = projectList.stream().filter(t->t.getStatus().equals(status)).collect(Collectors.toList());
        // 处理分页
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>projectList.size()?projectList.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>projectList.size()? projectList.size():(start + pageable.getPageSize());
        return new Resp("200", "获取成功", new PageImpl<>(projectList.subList(start,end), pageable, projectList.size()));
    }


    @ApiOperation(value = "获取项目详情", notes = "获取项目详情", httpMethod = "GET", response = Project.class, position = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数不能为空|无此项目")})
    @RequestMapping("/project/detail")
    public Resp getProjectDetail(@RequestParam(defaultValue = "") String projectUid) {

        // 检查角色
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        // 获取项目
        Project project = projectRepo.findByUid(projectUid.trim());
        if(project == null) return new Resp("400", "无此项目");
        return new Resp("200", "获取成功", project);
    }


    @ApiOperation(value = "删除项目", notes = "删除编辑中的项目，仅限创建人。", httpMethod = "POST", response = Resp.class, position = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "项目uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此项目|无权删除他人项目")})
    @RequestMapping("/project/del")
    public Resp delProject(@RequestParam(defaultValue = "") String projectUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        // 获取项目
        Project project = projectRepo.findByUid(projectUid.trim());
        if(project == null) return new Resp("400", "无此项目");
        if(!"EDIT".equals(project.getStatus())) return new Resp("400", "项目状态不在编辑中，不能删除");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(!creatorUid.equals(project.getCreatorUid())) return new Resp("400", "无权删除他人项目");
        // 检查项目附件
        List<ProjectMaterial> materials = projectMaterialRepo.findByProjectUid(projectUid.trim());
        if(materials.size() > 0) return new Resp("400", "请先删除项目附件");
        // 检查项目标段（包）
        List<Section> sections = sectionRepo.findByProjectUid(projectUid.trim());
        if(sections.size() > 0) return new Resp("400", "请先删除项目标段（包）");
        projectRepo.delete(project);
        // 更新操作记录
        opRecordService.createRecord("项目", project.getUid(), "删除项目", project.getName(), creatorUid, creatorName);
        return new Resp("200", "删除成功");
    }
}
