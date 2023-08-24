package com.hbzb.cloud.tender.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hbzb.cloud.tender.dao.ProjectRepo;
import com.hbzb.cloud.tender.entity.Project;
import com.hbzb.cloud.tender.model.Resp;
import com.hbzb.cloud.tender.service.RestService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目接口
 * http://192.168.33.200:8000/api/v1/tender-server/doc.html
 * created by dusizhong at 2020.01.14
 */
@Api(tags = "1.项目接口", description = "新建项目、更新项目、获取项目列表、获取项目详情")
@RestController
public class ProjectController {

    @Value("${uaa.server}")
    String UAA_SERVER;
    @Autowired
    RestService restService;
    @Autowired
    ProjectRepo projectRepo;

    @ApiOperation(value = "新建项目", httpMethod = "POST", response = Project.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serialNo", value = "项目编号"),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true),
            @ApiImplicitParam(name = "tenderer", value = "招标人", required = true),
            @ApiImplicitParam(name = "area", value = "所属辖区", required = true),
            @ApiImplicitParam(name = "address", value = "项目地址"),
            @ApiImplicitParam(name = "industry", value = "所属行业"),
            @ApiImplicitParam(name = "sourceOfFund", value = "资金来源"),
            @ApiImplicitParam(name = "scale", value = "项目规模"),
            @ApiImplicitParam(name = "contactName", value = "联系人"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功"),
            @ApiResponse(code = 400, message = "无权访问")})
    @RequestMapping("/project/create")
    public Resp createProject(@RequestParam(defaultValue = "") String serialNo,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String tenderer,
                              @RequestParam(defaultValue = "") String area,
                              @RequestParam(defaultValue = "") String address,
                              @RequestParam(defaultValue = "") String type,
                              @RequestParam(defaultValue = "") String industry,
                              @RequestParam(defaultValue = "") String sourceOfFund,
                              @RequestParam(defaultValue = "") String scale,
                              @RequestParam(defaultValue = "") String contactName,
                              @RequestParam(defaultValue = "") String contactPhone) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp(400, "权限不足");
        // check params
        if(StringUtils.isEmpty(name)) return new Resp(400, "项目名称不能为空");
        if(StringUtils.isEmpty(tenderer)) return new Resp(400, "招标人不能为空");
        // check tender

        // create serial no
        // 编码规则：采用组合码，编码长度为17位。
        // 1位平台类型代码 + 6位行政区域代码130100 + 4位全国公共服务平台序列号
        // 后6位由项目序列号组成，项目序列号的取值从000001-999999。
        if(StringUtils.isEmpty(serialNo)) serialNo = "E" + "130100" + "0001" + "000001";
        else {
            // check exist serialNo
            Project exist = projectRepo.findBySerialNo(serialNo.trim());
            if(exist != null) return new Resp(400, "项目编号已存在");
        }
        // get agency
        Map<String, String> params = new HashMap<>();
        params.put("corpUid", "1");
        JSONObject jsonCorp = restService.get(UAA_SERVER + "/corp/detail");
        if(jsonCorp == null) return new Resp(400, "获取招标代理信息失败");
        if(!"200".equals(jsonCorp.get("code"))) return new Resp(400, "UAA: " + jsonCorp.getString("message"));
        JSONObject corp = (JSONObject) JSONObject.toJSON(jsonCorp.get("data"));
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        System.out.println(jsonUser);
        // create project
        Project project = new Project();
        project.setSerialNo(serialNo.trim());
        project.setName(name.trim());
        project.setTenderer(tenderer.trim());
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
        project = projectRepo.saveAndFlush(project);
        return new Resp(200, "创建成功", project);
    }

    @ApiOperation(value = "更新项目", httpMethod = "POST", position = 2)
    @RequestMapping("/project/update")
    public Resp updateProject() {
        return new Resp(200, "更新成功");
    }

    @ApiOperation(value = "更新项目状态", httpMethod = "POST", position = 3)
    @RequestMapping("/project/update_status")
    public void updateProjectStatus() {

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
        if(!authorities.contains("USER") && !authorities.contains("MAN")) return new Resp(400, "权限不足");
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
}
