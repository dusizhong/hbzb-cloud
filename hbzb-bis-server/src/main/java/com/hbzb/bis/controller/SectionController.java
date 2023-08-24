package com.hbzb.bis.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.bis.dao.SectionRepo;
import com.hbzb.bis.entity.Section;
import com.hbzb.bis.model.Resp;
import com.hbzb.bis.service.UaaService;
import com.hbzb.bis.util.StringUtils;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SectionController {

    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;


    @RequestMapping("/section/list")
    public Resp getSectionList(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String status,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer size,
                               @RequestParam(defaultValue = "id,DESC") String sort) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
//        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
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
        if(!StringUtils.isEmpty(keyword)) sections = sections.stream().filter(s -> s.getKeyword().contains(keyword.trim())).collect(Collectors.toList());
        if(!StringUtils.isEmpty(status)) sections = sections.stream().filter(s -> s.getStatus().equals(status.trim())).collect(Collectors.toList());
        // 处理分页
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>sections.size()?sections.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>sections.size()? sections.size():(start + pageable.getPageSize());
        return new Resp("200", "获取成功", new PageImpl<>(sections.subList(start,end), pageable, sections.size()));
    }
}
