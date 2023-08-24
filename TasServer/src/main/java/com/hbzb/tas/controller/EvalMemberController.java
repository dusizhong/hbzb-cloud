package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.EvalMemberRepo;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.entity.EvalMember;
import com.hbzb.tas.entity.Section;
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
 * 开评标成员管理接口
 */
@RestController
public class EvalMemberController {

    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    EvalMemberRepo evalMemberRepo;


    @RequestMapping("/eval/member/create")
    public Resp createEvalMember(@RequestParam(defaultValue = "") String sortId,
                                 @RequestParam(defaultValue = "") String userUid,
                                 @RequestParam(defaultValue = "") String sectionUid,
                                 @RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String role,
                                 @RequestParam(defaultValue = "") String phone,
                                 @RequestParam(defaultValue = "") String work) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp("400", "权限不足");
        // 检查参数
        if (StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if (jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 检查用户信息

        // 检查是否已存在
        EvalMember exist = evalMemberRepo.findBySectionUidAndUserUid(sectionUid.trim(), userUid.trim());
        if(exist != null) return new Resp("400", "成员已存在");
        EvalMember evalMember = new EvalMember();
        evalMember.setSortId(0);
        evalMember.setSectionUid(sectionUid.trim());
        evalMember.setUserUid(userUid.trim());
        evalMember.setName(name.trim());
        evalMember.setRole(role.trim());
        evalMember.setPhone(phone.trim());
        evalMember.setWork(work.trim());
        evalMember.setCreatorUid(creatorUid);
        evalMember.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        evalMember.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        evalMember = evalMemberRepo.save(evalMember);
        return new Resp("200", "操作成功", evalMember);
    }


    @RequestMapping("/eval/member/update")
    public Resp updateEvalMember(@RequestParam(defaultValue = "") String sectionUid,
                                 @RequestParam(defaultValue = "") String userUid,
                                 @RequestParam(defaultValue = "") String sortId,
                                 @RequestParam(defaultValue = "") String leader) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp("400", "权限不足");
        // 检查参数
        if (StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if (StringUtils.isEmpty(userUid)) return new Resp("400", "用户uid不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if (jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 检查是否已存在
        EvalMember evalMember = evalMemberRepo.findBySectionUidAndUserUid(sectionUid.trim(), userUid.trim());
        if(evalMember == null) return new Resp("400", "无此成员");
        // 更新排序
        if(!StringUtils.isEmpty(sortId)) {
            if(!StringUtils.isInt(sortId)) return new Resp("400", "排序id数据类型无效");
            evalMember.setSortId(Integer.valueOf(sortId.trim()));
        }
        // 设置评委负责人
        if(!StringUtils.isEmpty(leader)) {
            if(leader.equals("是")) {
                List<EvalMember> evalMembers = evalMemberRepo.findBySectionUidOrderBySortId(sectionUid.trim());
                for(EvalMember e : evalMembers) {
                    e.setLeader("否");
                }
                evalMemberRepo.save(evalMembers);
                evalMember.setLeader("是");
            }
        }
        evalMember.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        evalMember = evalMemberRepo.save(evalMember);
        return new Resp("200", "操作成功", evalMember);
    }


    @RequestMapping("/eval/member/list")
    public Resp getEvalMemberList(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp("400", "权限不足");
        // 检查参数
        if (StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段（包）uid不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if (jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String userUid = user.getString("uid");
        // 获取数据
        List<EvalMember> bidMembers = evalMemberRepo.findBySectionUidOrderBySortId(sectionUid.trim());
        return new Resp("200", "获取成功", bidMembers);
    }

    /**
     * 获取
     * 用于判断用户是否有权进入开评标项目
     * @param sectionUid
     * @return
     */
    @RequestMapping("/eval/member/detail")
    public Resp getEvalMemberDetail(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查参数
        if (StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段（包）uid不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if (jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String userUid = user.getString("uid");
        // 检查成员
        EvalMember member = evalMemberRepo.findBySectionUidAndUserUid(sectionUid.trim(), userUid);
        if(member == null) return new Resp("400", "无此成员");
        return new Resp("200", "获取成功", member);
    }


    @RequestMapping("/eval/member/del")
    public Resp delEvalMember(@RequestParam(defaultValue = "") String id) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(id)) return new Resp("400", "成员id不能为空");
        if(!StringUtils.isInt(id)) return new Resp("400", "id格式错误");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if (jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 检查成员
        EvalMember member = evalMemberRepo.findOne(Integer.valueOf(id.trim()));
        if(member == null) return new Resp("400", "无此成员");
        if(!creatorUid.equals(member.getCreatorUid())) return new Resp("400", "此成员非你创建，无权删除");
        // 删除成员
        evalMemberRepo.delete(member);
        return new Resp("200", "获取成功", member);
    }
}
