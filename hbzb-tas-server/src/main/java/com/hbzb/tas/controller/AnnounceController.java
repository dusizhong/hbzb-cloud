package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.AnnounceMaterialRepo;
import com.hbzb.tas.dao.AnnounceRepo;
import com.hbzb.tas.dao.ProjectRepo;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.entity.Announce;
import com.hbzb.tas.entity.AnnounceMaterial;
import com.hbzb.tas.entity.Project;
import com.hbzb.tas.entity.Section;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.OpRecordService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 公告服务接口
 * @author dusizhong
 * @since 2020-02-05
 */
@Api(tags = "3.1 公告接口")
@RestController
public class AnnounceController {

    @Value("${res.url}")
    String RES_URL;
    @Value("${res.path}")
    String RES_PATH;
    @Autowired
    UaaService uaaService;
    @Autowired
    OpRecordService opRecordService;

    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    AnnounceRepo announceRepo;
    @Autowired
    AnnounceMaterialRepo announceMaterialRepo;


    @ApiOperation(value = "新增公告", httpMethod = "POST", response = Announce.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionUids", value = "所属分包uid，多个用逗号分割", required = true),
            @ApiImplicitParam(name = "title", value = "公告标题", required = true),
            @ApiImplicitParam(name = "attribute", value = "公告性质（1正常公告、2更正公告、9其他）", required = true),
            @ApiImplicitParam(name = "type", value = "公告类型（1招标公告、2资格预审公告、9其他）", required = true),
            @ApiImplicitParam(name = "startTenderTime", value = "报名开始/交易文件发售时间", required = true),
            @ApiImplicitParam(name = "endTenderTime", value = "报名结束/交易文件截止时间", required = true),
            @ApiImplicitParam(name = "media", value = "发布媒体", required = true),
            @ApiImplicitParam(name = "requirement", value = "供应商的资格要求"),
            @ApiImplicitParam(name = "memo", value = "备注"),
            @ApiImplicitParam(name = "content", value = "公告内容(富文本)", required = true),
            @ApiImplicitParam(name = "approverUid", value = "审批人uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增成功"),
            @ApiResponse(code = 400, message = "无权访问|参数不能为空|")})
    @RequestMapping("/announce/create")
    public Resp createAnnounce(@RequestParam(defaultValue = "") String uid,
                               @RequestParam(defaultValue = "") String sectionUids,
                               @RequestParam(defaultValue = "") String title,
                               @RequestParam(defaultValue = "") String type,
                               @RequestParam(defaultValue = "") String attribute,
                               @RequestParam(defaultValue = "") String media,
                               @RequestParam(defaultValue = "") String content,
                               @RequestParam(defaultValue = "") String memo,
                               @RequestParam(defaultValue = "") String approverUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(sectionUids)) return new Resp("400", "标段uids不能为空");
//        if(StringUtils.isEmpty(serialNo)) return new Resp("400", "公告编号不能为空");
//        if(serialNo.trim().length() > 26) return new Resp("400", "公告编号不能超过26位");
        if(StringUtils.isEmpty(title)) return new Resp("400", "公告标题不能为空");
        if(StringUtils.isEmpty(attribute)) return new Resp("400", "公告性质不能为空");
        if(StringUtils.isEmpty(type)) return new Resp("400", "公告类型不能为空");
        if(StringUtils.isEmpty(content)) return new Resp("400", "公告内容不能为空");
        List<String> uids = Arrays.asList(sectionUids.split(","));
        if(uids.size() < 1) return new Resp("400", "标段uids格式无效");
        // 检查标段信息
        List<Section> sections = sectionRepo.findByUidIn(uids);
        if(sections.size() < 1) return new Resp("400", "标段uid未找到相应的标段信息");
        Section ts0 = sections.get(0);
        if(StringUtils.isEmpty(ts0.getProjectUid())) return new Resp("400", "所属的项目uid为空");
        for(Section s : sections) {
            if (!ts0.getProjectUid().equals(s.getProjectUid())) return new Resp("400", "所提交的标段uid不属于同一个项目");
            if (!"APPROVAL".equals(s.getStatus())) return new Resp("400", "有未审核通过的标段");
        }
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
        // 新增/编辑公告
        Announce announce;
        if(StringUtils.isEmpty(uid)) {
            announce = new Announce();
            announce.setUid("a" + UUID.randomUUID().toString().replace("-", ""));
            announce.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            announce = announceRepo.findByUid(uid.trim());
            if(announce == null) return new Resp("400", "无此公告");
        }
        announce.setSectionUids(sectionUids.trim());
        announce.setTitle(title.trim());
        announce.setType(type.trim());
        announce.setAttribute(attribute.trim());
        announce.setMedia(media.trim());
        announce.setContent(content.trim());
        announce.setMemo(memo.trim());
        announce.setAgencyUid(agencyUid);
        announce.setAgencyName(agencyName);
        announce.setPublish("未发布");
        announce.setStatus("EDIT");
        announce.setApproverUid(approverUid.trim());
        announce.setCreatorUid(creatorUid);
        announce.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announce = announceRepo.saveAndFlush(announce);
        // 更新标段
//        for(Section s : sections) {
//            s.setAnnounceUid(announce.getUid());
//            s.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        }
//        sectionRepo.save(sections);
        return new Resp("200", "操作成功", announce);
    }


    @RequestMapping("/announce/modify")
    public Resp modifyAnnounce(@RequestParam(defaultValue = "") String orginUid,
                               @RequestParam(defaultValue = "") String uid,
                               @RequestParam(defaultValue = "") String title,
                               @RequestParam(defaultValue = "") String media,
                               @RequestParam(defaultValue = "") String content,
                               @RequestParam(defaultValue = "") String memo,
                               @RequestParam(defaultValue = "") String approverUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(orginUid)) return new Resp("400", "原公告uid不能为空");
        if(StringUtils.isEmpty(title)) return new Resp("400", "公告标题不能为空");
        if(StringUtils.isEmpty(content)) return new Resp("400", "公告内容不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 检查原公告
        Announce orginAnnounce = announceRepo.findByUid(orginUid.trim());
        if(orginAnnounce == null) return new Resp("400", "原公告uid无效");
        if(!"已发布".equals(orginAnnounce.getPublish())) return new Resp("400", "原公告不在发布状态");
        if(!creatorUid.equals(orginAnnounce.getCreatorUid())) return new Resp("400", "无权操作他人原公告");
        // 新增/编辑公告
        Announce announce;
        if(StringUtils.isEmpty(uid)) {
            announce = new Announce();
            announce.setUid("a" + UUID.randomUUID().toString().replace("-", ""));
            announce.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            announce = announceRepo.findByUid(uid.trim());
            if(announce == null) return new Resp("400", "无此公告");
        }
        announce.setSectionUids(orginAnnounce.getSectionUids());
        announce.setTitle(title.trim());
        announce.setType(orginAnnounce.getType());
        announce.setAttribute("变更公告");
        announce.setOrginUid(orginUid.trim());
        announce.setMedia(media.trim());
        announce.setContent(content.trim());
        announce.setMemo(memo.trim());
        announce.setAgencyUid(orginAnnounce.getAgencyUid());
        announce.setAgencyName(orginAnnounce.getAgencyName());
        announce.setPublish("未发布");
        announce.setStatus("EDIT");
        announce.setApproverUid(approverUid.trim());
        announce.setCreatorUid(creatorUid);
        announce.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announce = announceRepo.saveAndFlush(announce);
        // 更新原公告状态
        orginAnnounce.setStatus("CHANGE");
        orginAnnounce.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announceRepo.save(orginAnnounce);
        return new Resp("200", "操作成功", announce);
    }


    @ApiOperation(value = "提交公告", httpMethod = "POST", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceUid", value = "公告uid", required = true),
            @ApiImplicitParam(name = "operate", value = "操作类型(提交审核、无须审核)", required = true),
            @ApiImplicitParam(name = "memo", value = "备注"),
            @ApiImplicitParam(name = "approverUid", value = "审核人uid（提交审核必填）")
    })
    @RequestMapping("/announce/submit")
    public Resp submitAnnounce(@RequestParam(defaultValue = "") String announceUid,
                                @RequestParam(defaultValue = "") String operate,
                                @RequestParam(defaultValue = "") String memo,
                                @RequestParam(defaultValue = "") String approvalUid) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        if(StringUtils.isEmpty(operate)) return new Resp("400", "操作类型不能为空");
        if(!"提交审核".equals(operate) && !"无须审核".equals(operate)) return new Resp("400", "操作类型无效");
        // check announce
        Announce announce = announceRepo.findByUid(announceUid.trim());
        if(announce == null) return new Resp("400", "无此公告");
        if(!"EDIT".equals(announce.getStatus())) return new Resp("400", "公告状态不符");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(!creatorUid.equals(announce.getCreatorUid())) return new Resp("400", "无权提交他人公告");
        // submit tender doc
        if("提交审核".equals(operate)) {
            if(StringUtils.isEmpty(approvalUid)) return new Resp("400", "审核人uid不能为空");
            announce.setApproverUid(approvalUid.trim());
            announce.setStatus("SUBMIT");
        } else if("无须审核".equals(operate)) {
            announce.setStatus("APPROVAL");
        }
        announce.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announceRepo.save(announce);
        // create operate record
        opRecordService.createRecord("公告", announce.getUid(), operate, memo, creatorUid, creatorName);
        return new Resp("200", "操作成功");
    }



    @ApiOperation(value = "审核公告", httpMethod = "POST", position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceUid", value = "公告uid", required = true),
            @ApiImplicitParam(name = "operate", value = "操作类型(审核通过、审核不通过、转审核)", required = true),
            @ApiImplicitParam(name = "memo", value = "备注"),
            @ApiImplicitParam(name = "approverUid", value = "转审核人uid（转审核必填）")
    })
    @RequestMapping("/announce/approval")
    public Resp approvalAnnounce(@RequestParam(defaultValue = "") String announceUid,
                                 @RequestParam(defaultValue = "") String operate,
                                 @RequestParam(defaultValue = "") String memo,
                                 @RequestParam(defaultValue = "") String approvalUid) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        if(StringUtils.isEmpty(operate)) return new Resp("400", "操作类型不能为空");
        if(!"APPROVAL".equals(operate) && !"REJECT".equals(operate) && !"TRANSFER".equals(operate)) return new Resp("400", "操作类型无效");
        // check announce
        Announce announce = announceRepo.findByUid(announceUid.trim());
        if(announce == null) return new Resp("400", "无此公告");
        if(!"SUBMIT".equals(announce.getStatus())) return new Resp("400", "公告状态不符");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(!creatorUid.equals(announce.getApproverUid())) return new Resp("400", "无权审核");
        // approval tender doc
        if("APPROVAL".equals(operate)) {
            announce.setStatus("APPROVAL");
        } else if("REJECT".equals(operate)) {
            announce.setStatus("REJECT");
        } else if("TRANSFER".equals(operate)) {
            if(StringUtils.isEmpty(approvalUid)) return new Resp("400", "转审核人uid不能为空");
            announce.setApproverUid(approvalUid.trim());
            announce.setStatus("SUBMIT");
        }
        announce.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announceRepo.save(announce);
        // create operate record
        opRecordService.createRecord("公告", announce.getUid(), operate, memo, creatorUid, creatorName);
        return new Resp("200", "操作成功");
    }



    @ApiOperation(value = "发布公告", httpMethod = "POST", position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceUid", value = "公告uid", required = true),
            @ApiImplicitParam(name = "operate", value = "操作类型(发布公告、停止公告)", required = true),
    })
    @RequestMapping("/announce/publish")
    public Resp publishAnnounce(@RequestParam(defaultValue = "") String announceUid,
                                 @RequestParam(defaultValue = "") String operate) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        if(StringUtils.isEmpty(operate)) return new Resp("400", "操作类型不能为空");
        if(!"发布公告".equals(operate) && !"停止公告".equals(operate)) return new Resp("400", "操作类型无效");
        // check announce
        Announce announce = announceRepo.findByUid(announceUid.trim());
        if(announce == null) return new Resp("400", "无此公告");
        if(!"APPROVAL".equals(announce.getStatus())) return new Resp("400", "公告暂未审核通过");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
//        if(!creatorUid.equals(announce.getApproverUid())) return new Resp("400", "无权发布");
        // publish announce
        if("发布公告".equals(operate)) {
            if("已发布".equals(announce.getPublish())) return new Resp("400", "公告已发布");
            announce.setPublish("已发布");
            announce.setStatus("PUBLISH");
            announce.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else if("停止公告".equals(operate)) {
            if(!"已发布".equals(announce.getPublish())) return new Resp("400", "公告暂未发布或已停止");
            announce.setPublish("已停止");
            announce.setStatus("PUBLISH");
        }
        announce.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announceRepo.save(announce);
        // create operate record
        opRecordService.createRecord("公告", announce.getUid(), operate, "", creatorUid, creatorName);
        return new Resp("200", "操作成功");
    }


    @ApiOperation(value = "获取公告列表", notes = "获取公告列表（与原平台有所不同，按标题、编号搜索，无按分包搜索）。",
            httpMethod = "GET", response = Page.class, position = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "公告标题"),
            @ApiImplicitParam(name = "serialNo", value = "公告编号"),
            @ApiImplicitParam(name = "status", value = "状态(编辑中、待审核、审核通过、审核不通过)"),
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页记录数"),
            @ApiImplicitParam(name = "sort", value = "排序值（默认：id,DESC）")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数有误")})
    @RequestMapping("/announce/list")
    public Resp getAnnounceList(@RequestParam(defaultValue = "") String title,
                                @RequestParam(defaultValue = "") String serialNo,
                                @RequestParam(defaultValue = "") String status,
                                @RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "20") Integer size,
                                @RequestParam(defaultValue = "id,DESC") String sort) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
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
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // 获取数据
        List<Announce> announceList = announceRepo.findByAgencyUid(agencyUid, mySort);
        if(!StringUtils.isEmpty(title)) announceList = announceList.stream().filter(a->a.getTitle().contains(title)).collect(Collectors.toList());
//        if(!StringUtils.isEmpty(serialNo)) announceList = announceList.stream().filter(a->a.getSerialNo().equals(serialNo)).collect(Collectors.toList());
        if(!StringUtils.isEmpty(status)) announceList = announceList.stream().filter(a->a.getStatus().equals(status)).collect(Collectors.toList());
        // 组装分页
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>announceList.size()?announceList.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>announceList.size()? announceList.size():(start + pageable.getPageSize());
        PageImpl data = new PageImpl<>(announceList.subList(start,end), pageable, announceList.size());
        return new Resp("200", "获取成功", data);
    }



    @ApiOperation(value = "获取公告详情", notes = "获取公告详情",
            httpMethod = "GET", response = Announce.class, position = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceUid", value = "公告uid")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "参数不能为空|无此招标项目")})
    @RequestMapping("/announce/detail")
    public Resp getAnnounceDetail(@RequestParam(defaultValue = "") String announceUid) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        // 检查公告
        Announce announce = announceRepo.findByUid(announceUid.trim());
        if(announce == null) return new Resp("400", "无此公告");
        // 获取公告相关附件
        List<AnnounceMaterial> materials = announceMaterialRepo.findByAnnounceUid(announceUid);
        materials.forEach(material -> material.setUrl(RES_URL + "/announce/" + announce.getUid() + "/" + material.getUrl()));
        // 组装返回数据
        JSONObject data = (JSONObject) JSONObject.toJSON(announce);
        data.put("materials", materials);
        return new Resp("200", "获取成功", data);
    }


    @RequestMapping("/announce/del")
    public Resp delAnnounce(@RequestParam(defaultValue = "") String announceUid) {
        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        // 检查公告
        Announce announce = announceRepo.findByUid(announceUid.trim());
        if(announce == null) return new Resp("400", "无此公告");
        if(!"EDIT".equals(announce.getStatus())) return new Resp("400", "公告不在编辑状态，不能删除");
        if(!creatorUid.equals(announce.getCreatorUid())) return new Resp("400", "无权删除他人公告");
        // 删除公告
        announceRepo.delete(announce);
        // 删除相关附件
        List<AnnounceMaterial> materials = announceMaterialRepo.findByAnnounceUid(announceUid.trim());
        announceMaterialRepo.delete(materials);
        // 更新操作记录
        opRecordService.createRecord("公告", announce.getUid(), "删除公告", announce.getTitle(), creatorUid, creatorName);
        return new Resp("200", "删除成功");
    }
}
