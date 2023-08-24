package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.*;
import com.hbzb.tas.entity.*;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.RestService;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * 投标邀请服务接口
 * @author dusizhong
 * @since 2020-02-07
 */
@Api(tags = "4.2 投标邀请接口", description = "投标邀请接口服务。")
@RestController
public class InvitationController {

    @Autowired
    UaaService uaaService;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    InvitationRepo invitationRepo;
    @Autowired
    InvitationTemplateRepo invitationTemplateRepo;
    @Autowired
    ApprovalRecordRepo approvalRecordRepo;


    @ApiOperation(value = "新增投标邀请", httpMethod = "POST", response = Invitation.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenderSectionUid", value = "所属分包uid", required = true),
            @ApiImplicitParam(name = "qualification", value = "投标资格", required = true),
            @ApiImplicitParam(name = "tenderDocPrice", value = "招标文件工本费（元）", required = true),
            @ApiImplicitParam(name = "tenderDocStartTime", value = "招标文件发售时间", required = true),
            @ApiImplicitParam(name = "tenderDocEndTime", value = "招标文件发售截止时间", required = true),
            @ApiImplicitParam(name = "tenderDocGetMethod", value = "招标文件获取方法"),
            @ApiImplicitParam(name = "replyDeadline", value = "回复截至时间", required = true),
            @ApiImplicitParam(name = "bidDocPostMethod", value = "投标文件递交方法"),
            @ApiImplicitParam(name = "approverUid", value = "审批人uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增成功"),
            @ApiResponse(code = 400, message = "无权访问|参数不能为空|")})
    @RequestMapping("/invitation/create")
    public Resp createInvitation(@RequestParam(defaultValue = "") String uid,
                                 @RequestParam(defaultValue = "") String sectionUids,
                                 @RequestParam(defaultValue = "") String replyDeadline,
                                 @RequestParam(defaultValue = "") String content,
                                 @RequestParam(defaultValue = "") String approverUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(sectionUids)) return new Resp("400", "所属标段uid不能为空");
        if(StringUtils.isEmpty(content)) return new Resp("400", "邀请函内容不能为空");
        if(StringUtils.isEmpty(replyDeadline)) return new Resp("400", "回复截至时间不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 获取代理
        JSONObject jsonCorp = uaaService.requestCorpDetail();
        if(jsonCorp == null) return new Resp("400", "UAA: 获取招标代理信息失败");
        if(!"200".equals(jsonCorp.get("code"))) return new Resp("400", "UAA: " + jsonCorp.getString("message"));
        JSONObject agency = (JSONObject) JSONObject.toJSON(jsonCorp.get("data"));
        String agencyUid = agency.getString("uid");
        String agencyName = agency.getString("name");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取招标代理uid失败");
        if(StringUtils.isEmpty(agencyName)) return new Resp("400", "UAA: 获取招标代理名称失败");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUids.trim());
        if (section == null) return new Resp("400", "无此标段");
        if (!"APPROVAL".equals(section.getStatus())) return new Resp("400", "此标段未审核通过，不能操作");
        if (!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 模板组装邀请函
        InvitationTemplate template = invitationTemplateRepo.findByAgencyUid(agencyUid);
        if(template != null) {

        }
        // 新增/更新邀请函
        Invitation invitation;
        if(StringUtils.isEmpty(uid)) {
            invitation = new Invitation();
            invitation.setUid("it" + UUID.randomUUID().toString().replace("-", ""));
            invitation.setStatus("EDIT");
            invitation.setCreatorUid(creatorUid);
            invitation.setAgencyUid(agencyUid);
            invitation.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            invitation = invitationRepo.findByUid(uid.trim());
            if(invitation == null) return new Resp("400", "无此邀请函");
            if("EDIT".equals(invitation.getStatus())) return new Resp("400", "邀请函状态不在编辑中，不能操作");
            if(creatorUid.equals(invitation.getCreatorUid())) return new Resp("400", "此邀请函非你创建，无权操作");
        }
        invitation.setSectionUids(sectionUids.trim());
        invitation.setTitle(section.getName());
        invitation.setContent(content.trim());
        invitation.setReplyDeadline(replyDeadline.trim());
        invitation.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        invitation = invitationRepo.save(invitation);
        return new Resp("200", "新增成功", invitation);
    }


//    @ApiOperation(value = "更新邀请函状态", httpMethod = "POST", position = 3)
//    @RequestMapping("/invitation/status/update")
//    public Resp updateInvitationStatus(@RequestParam(defaultValue = "") String invitationUid,
//                                     @RequestParam(defaultValue = "") String status) {
//        // check authorities
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String authorities = auth.getAuthorities().toString();
//        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
//        // check params
//        if(StringUtils.isEmpty(invitationUid)) return new Resp("400", "邀请函uid不能为空");
//        if(StringUtils.isEmpty(status)) return new Resp("400", "状态不能为空");
//        if(!status.equals("EDIT") && !status.equals("SUBMIT") && !status.equals("APPROVAL")
//                && !status.equals("REJECT")) return new Resp("400", "状态值无效");
//        // check invitation
//        Invitation invitation = invitationRepo.findByUid(invitationUid.trim());
//        if(invitation == null) return new Resp("400", "无此邀请函");
//        // check user
//        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
//        if(jsonUser == null) return new Resp("400", "获取用户信息失败");
//        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
//        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
//        String creatorUid = user.getString("uid");
//        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "获取用户uid失败");
////        if(!project.getCreatorUid().equals(user.get("uid"))) return new Resp("400", "无权修改他人项目");
//        // update status
//        invitation.setStatus(status);
//        invitation.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        invitationRepo.save(invitation);
//        // create approval record
//        ApprovalRecord approvalRecord = new ApprovalRecord();
//        approvalRecord.setItemType("邀请函");
//        approvalRecord.setItemUid(invitationUid);
//        approvalRecord.setStep("审核");
//        approvalRecord.setOpinion(status);
//        approvalRecord.setCreatorUid(creatorUid);
//        approvalRecord.setCreatorName(user.getString("username"));
//        approvalRecord.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        approvalRecordRepo.save(approvalRecord);
//        return new Resp("200", "更新成功");
//    }


    @ApiOperation(value = "获取邀请函列表", notes = "获取邀请函列表。",
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
            @ApiResponse(code = 400, message = "参数有误")})
    @RequestMapping("/invitation/list")
    public Resp getInvitationList(@RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(defaultValue = "") String status,
                                  @RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "20") Integer size,
                                  @RequestParam(defaultValue = "id,DESC") String sort) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        // if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp("400", "权限不足");
        // 检查参数
        Sort mySort;
        String[] sorts = sort.split(",");
        if(sorts.length < 2) return new Resp("400", "排序参数格式错误");
        if(sorts[1].equals("ASC")) mySort = new Sort(Sort.Direction.ASC, sorts[0]);
        else if(sorts[1].equals("DESC")) mySort = new Sort(Sort.Direction.DESC, sorts[0]);
        else return new Resp("400", "排序参数无效");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "获取用户corpUid失败");
        List<Invitation> invitationList = invitationRepo.findByAgencyUid(agencyUid, mySort);
        // 组装分页
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>invitationList.size()?invitationList.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>invitationList.size()? invitationList.size():(start + pageable.getPageSize());
        return new Resp("200", "获取成功", new PageImpl<>(invitationList.subList(start,end), pageable, invitationList.size()));
    }



    @ApiOperation(value = "获取邀请函详情", notes = "获取邀请函详情",
            httpMethod = "GET", response = Invitation.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "invitationUid", value = "邀请函uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数不能为空|无此邀请函")})
    @RequestMapping("/invitation/detail")
    public Resp getInvitationDetail(@RequestParam(defaultValue = "") String invitationUid) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(invitationUid)) return new Resp("400", "邀请函uid不能为空");
        // 获取邀请函
        Invitation invitation = invitationRepo.findByUid(invitationUid.trim());
        if(invitation == null) return new Resp("400", "无此邀请函");
        // 获取项目标段信息
        Section section = sectionRepo.findByUid(invitation.getSectionUids());
        if(section == null) return new Resp("400", "未获取到此邀请函所属标段信息");
        Project project = projectRepo.findByUid(section.getProjectUid());
        if(project == null) return new Resp("400", "未获取到此邀请函所属项目信息");
        // 组装数据
        JSONObject data = (JSONObject)JSONObject.toJSON(invitation);
        data.put("project", project);
        data.put("section", section);
        return new Resp("200", "获取成功", data);
    }
}
