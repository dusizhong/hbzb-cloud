package com.hbzb.cloud.tds.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.cloud.tds.dao.ApprovalRecordRepo;
import com.hbzb.cloud.tds.dao.ProjectRepo;
import com.hbzb.cloud.tds.entity.ApprovalRecord;
import com.hbzb.cloud.tds.entity.Project;
import com.hbzb.cloud.tds.model.Resp;
import com.hbzb.cloud.tds.service.RestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 项目接口
 * created by dusizhong at 2020.01.14
 */
@Api(tags = "1.项目接口", description = "新建项目、更新项目、获取项目列表、获取项目详情、删除项目")
@RestController
public class ProjectController {

    @Value("${uaa.server}")
    String UAA_SERVER;
    @Autowired
    RestService restService;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    ApprovalRecordRepo approvalRecordRepo;


    @ApiOperation(value = "新建项目", httpMethod = "POST", response = Project.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serialNo", value = "项目编号"),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true),
            @ApiImplicitParam(name = "tenderer", value = "采购单位", required = true),
            @ApiImplicitParam(name = "area", value = "所属辖区", required = true),
            @ApiImplicitParam(name = "address", value = "采购人地址"),
            @ApiImplicitParam(name = "contactName", value = "采购联系人"),
            @ApiImplicitParam(name = "contactPhone", value = "采购联系电话"),
            @ApiImplicitParam(name = "tradeCategory", value = "采购交易分类（货物类（含药品集中采购）、工程类、服务类）", required = true),
            @ApiImplicitParam(name = "content", value = "项目内容")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功"),
            @ApiResponse(code = 400, message = "无权访问")})
    @RequestMapping("/project/create")
    public Resp createProject(@RequestParam(defaultValue = "") String serialNo,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String tenderer,
                              @RequestParam(defaultValue = "") String contactName,
                              @RequestParam(defaultValue = "") String contactPhone,
                              @RequestParam(defaultValue = "") String area,
                              @RequestParam(defaultValue = "") String address,
                              @RequestParam(defaultValue = "") String tradeCategory,
                              @RequestParam(defaultValue = "") String content) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp(400, "权限不足");
        // check params
        if(StringUtils.isEmpty(serialNo)) return new Resp(400, "项目编号不能为空");
        if(StringUtils.isEmpty(name)) return new Resp(400, "项目名称不能为空");
        if(StringUtils.isEmpty(tenderer)) return new Resp(400, "招标人不能为空");
        if(StringUtils.isEmpty(area)) return new Resp(400, "所在辖区不能为空");
        if(StringUtils.isEmpty(tradeCategory)) return new Resp(400, "采购交易分类不能为空");
        // check serial no
        Project existSerialNo = projectRepo.findBySerialNo(serialNo.trim());
        if(existSerialNo != null) return new Resp(400, "项目编号已存在");
        // get agency
        JSONObject jsonCorp = restService.get(UAA_SERVER + "/corp/detail");
        if(jsonCorp == null) return new Resp(400, "获取招标代理信息失败");
        if(!"200".equals(jsonCorp.get("code"))) return new Resp(400, "UAA: " + jsonCorp.getString("message"));
        JSONObject corp = (JSONObject) JSONObject.toJSON(jsonCorp.get("data"));
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        // check or create serial no
        if(!StringUtils.isEmpty(serialNo)) { //custom serial no
            if(serialNo.length() > 17) return new Resp(400, "项目编号不能超过17位");
            //if(serialNo.trim().startsWith("A1301001001")) return new Resp(400, "项目编号不能使用自动生成规则");
            Project exist = projectRepo.findBySerialNo(serialNo.trim());
            if(exist != null) return new Resp(400, "项目编号已存在");
        } else {
            // 生成编号
            // 交易平台标识代码：
            // 采用组合码，编码长度为11位。
            // 1位行业门类字母码、6位行政区域代码，以及4位全国交易平台序列号
            // "A" + "130100" + "0001"
            // 项目编号：
            // 采用组合码，编码长度为17位。
            // 前11位由交易平台标识代码组成，后6位由项目序列号组成，项目序列号的取值从000001-999999。
            serialNo = "A" + "130100" + "1001" + "000001";
            Project last = projectRepo.findFirstByOrderByIdDesc();
            String tailNo = last.getSerialNo().substring(11);

        }
        // create project
        Project project = new Project();
        project.setUid("p" + UUID.randomUUID());
        project.setSerialNo(serialNo.trim());
        project.setName(name.trim());
        project.setTendererName(tenderer.trim());
        project.setTendererUniformCode("sdfsdfsdf");
        project.setArea(area.trim());
        project.setAddress(address.trim());
        project.setType(type.trim());
        project.setIndustry(industry.trim());
        project.setSourceOfFund(sourceOfFund.trim());
        project.setScale(scale.trim());
        project.setContactName(contactName);
        project.setContactPhone(contactPhone);
        project.setStatus("EDIT");
        project.setCorpUid(corp.getString("uid"));
        project.setCreatorUid(user.getString("uid"));
        project.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        project.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        project = projectRepo.save(project);
        return new Resp(200, "创建成功", project);
    }

    /**
     * 更新项目
     * @param projectUid
     * @param serialNo
     * @param name
     * @param tenderer
     * @param type
     * @param area
     * @param address
     * @param industry
     * @param sourceOfFund
     * @param scale
     * @param contactName
     * @param contactPhone
     * @return
     */
    @ApiOperation(value = "更新项目", httpMethod = "POST", response = Project.class, position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid", required = true),
            @ApiImplicitParam(name = "serialNo", value = "项目编号"),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true),
            @ApiImplicitParam(name = "tenderer", value = "招标人", required = true),
            @ApiImplicitParam(name = "area", value = "所属辖区", required = true),
            @ApiImplicitParam(name = "address", value = "项目地址"),
            @ApiImplicitParam(name = "type", value = "采购交易分类（货物类（含药品集中采购）、工程类、服务类）"),
            @ApiImplicitParam(name = "industry", value = "所属行业"),
            @ApiImplicitParam(name = "sourceOfFund", value = "资金来源"),
            @ApiImplicitParam(name = "scale", value = "项目规模"),
            @ApiImplicitParam(name = "contactName", value = "联系人"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此项目|获取用户信息失败")})
    @RequestMapping("/project/update")
    public Resp updateProject(@RequestParam(defaultValue = "") String projectUid,
                              @RequestParam(defaultValue = "") String serialNo,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String tenderer,
                              @RequestParam(defaultValue = "") String type,
                              @RequestParam(defaultValue = "") String area,
                              @RequestParam(defaultValue = "") String address,
                              @RequestParam(defaultValue = "") String industry,
                              @RequestParam(defaultValue = "") String sourceOfFund,
                              @RequestParam(defaultValue = "") String scale,
                              @RequestParam(defaultValue = "") String contactName,
                              @RequestParam(defaultValue = "") String contactPhone) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(projectUid)) return new Resp(400, "项目uid不能为空");
        if(StringUtils.isEmpty(serialNo)) return new Resp(400, "项目编号不能为空");
        if(StringUtils.isEmpty(name)) return new Resp(400, "项目名称不能为空");
        if(StringUtils.isEmpty(tenderer)) return new Resp(400, "招标人不能为空");
        if(StringUtils.isEmpty(type)) return new Resp(400, "采购交易分类不能为空");
        if(StringUtils.isEmpty(area)) return new Resp(400, "所在辖区不能为空");
        // check project
        Project project = projectRepo.findByUid(projectUid);
        if(project == null) return new Resp(400, "无此项目");
        if(!project.getStatus().equals("EDIT")) return new Resp(400, "项目状态不符");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        if(!project.getCreatorUid().equals(user.get("uid"))) return new Resp(400, "无权修改他人项目");
        // check serial no
        Project existProject = projectRepo.findBySerialNo(serialNo);
        if(existProject != null && !existProject.getId().equals(project.getId())) return new Resp(400, "项目编号已存在");
        // update project
        project.setSerialNo(serialNo.trim());
        project.setName(name.trim());
        project.setTendererName(tenderer.trim());
        project.setTendererUniformCode("asdfasdfsf");
        project.setType(type.trim());
        project.setArea(area.trim());
        project.setAddress(address.trim());
        project.setIndustry(industry.trim());
        project.setSourceOfFund(sourceOfFund.trim());
        project.setScale(scale.trim());
        project.setContactName(contactName);
        project.setContactPhone(contactPhone);
        project.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        project = projectRepo.save(project);
        return new Resp(200, "更新成功", project);
    }

    @ApiOperation(value = "更新项目状态", httpMethod = "POST", position = 3)
    @RequestMapping("/project/status/update")
    public Resp updateProjectStatus(@RequestParam(defaultValue = "") String projectUid,
                                    @RequestParam(defaultValue = "") String status) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(projectUid)) return new Resp(400, "项目uid不能为空");
        if(StringUtils.isEmpty(status)) return new Resp(400, "状态不能为空");
        if(!status.equals("EDIT") && !status.equals("SUBMIT") && !status.equals("APPROVAL")
        && !status.equals("REJECT")) return new Resp(400, "状态值无效");
        // check project
        Project project = projectRepo.findByUid(projectUid);
        if(project == null) return new Resp(400, "无此项目");
        // check user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
//        if(!project.getCreatorUid().equals(user.get("uid"))) return new Resp(400, "无权修改他人项目");
        // update status
        project.setStatus(status);
        project.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        projectRepo.save(project);
        // create approval record
        ApprovalRecord approvalRecord = new ApprovalRecord();
        approvalRecord.setItemType("项目");
        approvalRecord.setItemUid(projectUid);
        approvalRecord.setStep("审核");
        approvalRecord.setOpinion(status);
        approvalRecord.setCreatorUid(user.getString("uid"));
        approvalRecord.setCreatorName(user.getString("username"));
        approvalRecord.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        approvalRecordRepo.save(approvalRecord);
        return new Resp(200, "更新成功");
    }

    @ApiOperation(value = "获取项目列表", notes = "获取项目列表",
            httpMethod = "GET", response = Page.class, position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页记录数"),
            @ApiImplicitParam(name = "sort", value = "排序值（默认：id,DESC）")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符|权限不足")})
    @RequestMapping("/project/list")
    public Resp getProjectList(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String status,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer size,
                               @RequestParam(defaultValue = "id,DESC") String sort) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
//        if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp(400, "权限不足");
        // check params
        Sort mySort;
        String[] sorts = sort.split(",");
        if(sorts.length < 2) return new Resp(400, "排序参数格式错误");
        if(sorts[1].equals("ASC")) mySort = new Sort(Sort.Direction.ASC, sorts[0]);
        else if(sorts[1].equals("DESC")) mySort = new Sort(Sort.Direction.DESC, sorts[0]);
        else return new Resp(400, "排序参数无效");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        List<Project> projectList = projectRepo.findByCorpUid(user.getString("corpUid"), mySort);
        // prepare page
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>projectList.size()?projectList.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>projectList.size()? projectList.size():(start + pageable.getPageSize());
        return new Resp(200, "获取成功", new PageImpl<>(projectList.subList(start,end), pageable, projectList.size()));
    }

    @ApiOperation(value = "获取项目详情", notes = "获取项目详情",
            httpMethod = "GET", response = Project.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数不能为空|无此项目")})
    @RequestMapping("/project/detail")
    public Resp getProjectDetail(@RequestParam(defaultValue = "") String projectUid) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(projectUid)) return new Resp(400, "项目uid不能为空");
        // check project
        Project project = projectRepo.findByUid(projectUid);
        if(project == null) return new Resp(400, "无此项目");
        return new Resp(200, "获取成功", project);
    }
}
