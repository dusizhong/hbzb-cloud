package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.ApprovalRecordRepo;
import com.hbzb.tas.dao.InvitationRepo;
import com.hbzb.tas.dao.InviteRecordRepo;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.entity.ApprovalRecord;
import com.hbzb.tas.entity.Invitation;
import com.hbzb.tas.entity.InviteRecord;
import com.hbzb.tas.model.Resp;
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

/**
 * 邀请单位服务接口
 * @author dusizhong
 * @since 2020-02-07
 */
@Api(tags = "4.1 邀请投标人函接口", description = "邀请投标人函接口服务。")
@RestController
public class InviteRecordController {

    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    InviteRecordRepo inviteRecordRepo;
    @Autowired
    InvitationRepo invitationRepo;


    @ApiOperation(value = "新增邀请投标人", httpMethod = "POST", response = InviteRecord.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "invitationUid", value = "所属邀请函uid", required = true),
            @ApiImplicitParam(name = "bidderUid", value = "投标人uid", required = true),
            @ApiImplicitParam(name = "bidderName", value = "投标人名称", required = true),
            @ApiImplicitParam(name = "bidderMan", value = "投标人项目负责人", required = true),
            @ApiImplicitParam(name = "unionBidder", value = "是否联合体投标", required = true),
            @ApiImplicitParam(name = "approverUid", value = "审批人uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增成功"),
            @ApiResponse(code = 400, message = "无权访问|参数不能为空|")})
    @RequestMapping("/invite/record/create")
    public Resp createInviteRecord(
                                   @RequestParam(defaultValue = "") String invitationUid,
                                   @RequestParam(defaultValue = "") String bidderUid,
                                   @RequestParam(defaultValue = "") String bidderName,
                                   @RequestParam(defaultValue = "") String bidderUniformCode) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(invitationUid)) return new Resp("400", "邀请函uid不能为空");
        if(StringUtils.isEmpty(bidderUid)) return new Resp("400", "投标人uid不能为空");
        if(StringUtils.isEmpty(bidderName)) return new Resp("400", "投标人名称不能为空");
        // 检查邀请函信息
        Invitation invitation = invitationRepo.findByUid(invitationUid.trim());
        if(invitation == null) return new Resp("400", "所属邀请函不存在");
        // 检查投标人信息
        // JSONObject bidder = uaaService.requestBidderDetail(bidderUid);
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取招标代理uid失败");
        // 检查是否已添加
        InviteRecord exist1 = inviteRecordRepo.findByInvitationUidAndBidderName(invitationUid.trim(), bidderName.trim());
        if(exist1 != null) return new Resp("400", "此投标人已添加");
        InviteRecord exist2 = inviteRecordRepo.findByInvitationUidAndBidderUniformCode(invitationUid.trim(), bidderUniformCode.trim());
        if(exist2 != null) return new Resp("400", "此投标人已添加");
        // 新增/修改邀请
        InviteRecord inviteRecord = new InviteRecord();
        inviteRecord.setUid(UUID.randomUUID().toString().replace("-", ""));
        inviteRecord.setInvitationUid(invitationUid.trim());
        inviteRecord.setBidderUid(bidderUid.trim());
        inviteRecord.setBidderName(bidderName.trim());
        inviteRecord.setBidderUniformCode(bidderUniformCode.trim());
        inviteRecord.setStatus("新增");
        inviteRecord.setAgencyUid(agencyUid);
        inviteRecord.setCreatorUid(creatorUid);
        inviteRecord.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        inviteRecord.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        inviteRecord = inviteRecordRepo.save(inviteRecord);
        return new Resp("200", "新增成功", inviteRecord);
    }



    @ApiOperation(value = "获取邀请投标人列表", notes = "获取邀请投标人列表。",
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
    @RequestMapping("/invite/record/list")
    public Resp getInveRecordList(@RequestParam(defaultValue = "") String invitationUid,
                                  @RequestParam(defaultValue = "") String keyword,
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
        if(StringUtils.isEmpty(invitationUid)) return new Resp("400", "邀请函uid不能为空");
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
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取招标代理uid失败");
        // 获取数据
        List<InviteRecord> inviteRecords = inviteRecordRepo.findByInvitationUidAndAgencyUid(invitationUid, agencyUid, mySort);
        // 组装分页
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>inviteRecords.size()?inviteRecords.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>inviteRecords.size()? inviteRecords.size():(start + pageable.getPageSize());
        return new Resp("200", "获取成功", new PageImpl<>(inviteRecords.subList(start,end), pageable, inviteRecords.size()));
    }


    @ApiOperation(value = "获取邀请投标人详情", notes = "获取邀请投标人详情",
            httpMethod = "GET", response = InviteRecord.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inviteBidderUid", value = "邀请投标人uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数不能为空|无此邀请函")})
    @RequestMapping("/invite/record/detail")
    public Resp getInviteRecordDetail(@RequestParam(defaultValue = "") String uid) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(uid)) return new Resp("400", "邀请记录uid不能为空");
        // check invitation
        InviteRecord inviteRecord = inviteRecordRepo.findByUid(uid.trim());
        if(inviteRecord == null) return new Resp("400", "无此邀请记录");

        return new Resp("200", "获取成功", inviteRecord);
    }



    @RequestMapping("/invite/record/del")
    public Resp delInviteRecord(@RequestParam(defaultValue = "") String uid) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(uid)) return new Resp("400", "邀请记录uid不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 检查记录
        InviteRecord inviteRecord = inviteRecordRepo.findByUid(uid.trim());
        if(inviteRecord == null) return new Resp("400", "无此邀请记录");
        if("已发出".equals(inviteRecord.getStatus())) return new Resp("400", "此邀请已发出，不能删除");
        if(!creatorUid.equals(inviteRecord.getCreatorUid())) return new Resp("400", "此邀请记录非你创建，无权操作");
        // 删除记录
        inviteRecordRepo.delete(inviteRecord);
        return new Resp("200", "删除成功");
    }
}
