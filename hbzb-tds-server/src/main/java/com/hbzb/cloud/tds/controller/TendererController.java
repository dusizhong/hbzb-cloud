package com.hbzb.cloud.tds.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.cloud.tds.dao.TendererRepo;
import com.hbzb.cloud.tds.entity.Tenderer;
import com.hbzb.cloud.tds.model.Resp;
import com.hbzb.cloud.tds.service.RestService;
import com.hbzb.cloud.tds.util.StringUtils;
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
import java.util.stream.Collectors;

/**
 * 采购单位接口服务
 * created by dusizhong at 2020.01.23
 */
@Api(tags = "采购单位接口", description = "用于新增项目时选择采购单位，仅单位内可见。")
@RestController
public class TendererController {

    @Value("${uaa.server}")
    String UAA_SERVER;
    @Autowired
    RestService restService;
    @Autowired
    TendererRepo tendererRepo;

    /**
     * 新增采购单位
     * @param name
     * @param uniformCode
     * @param contactName
     * @param contactPhone
     * @return
     */
    @ApiOperation(value = "新增采购单位", notes="新增采购单位接口服务。",
            httpMethod = "POST", response = Tenderer.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "单位名称", required = true),
            @ApiImplicitParam(name = "uniformCode", value = "统一社会信用代码"),
            @ApiImplicitParam(name = "contactName", value = "联系人"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "新增成功"),
            @ApiResponse(code=400, message = "参数不能为空|单位名称已存在|统一信用代码已存在")})
    @RequestMapping("/tenderer/create")
    public Resp createTenderer(@RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String uniformCode,
                           @RequestParam(defaultValue = "") String contactName,
                           @RequestParam(defaultValue = "") String contactPhone) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(name)) return new Resp(400, "单位名称不能为空");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        // check exist
        Tenderer existName = tendererRepo.findByNameAndAgencyUid(name, agencyUid);
        if(existName != null) return new Resp(400, "单位名称已存在");
        if(!StringUtils.isEmpty(uniformCode)) {
            Tenderer existUniformCode = tendererRepo.findByUniformCodeAndAgencyUid(uniformCode, agencyUid);
            if(existUniformCode != null) return new Resp(400, "统一社会信用代码已存在");
        }
        // create Tenderer
        Tenderer tenderer = new Tenderer();
        tenderer.setName(name);
        tenderer.setUniformCode(uniformCode);
        tenderer.setContactName(contactName);
        tenderer.setContactPhone(contactPhone);
        tenderer.setAgencyUid(agencyUid);
        tenderer.setCreatorUid(user.getString("uid"));
        tenderer.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderer.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderer = tendererRepo.save(tenderer);
        return new Resp(200, "创建成功", tenderer);
    }

    /**
     * 更新采购单位
     * @param tendererId
     * @param name
     * @param uniformCode
     * @param contactName
     * @param contactPhone
     * @return
     */
    @ApiOperation(value = "更新采购单位", notes="更新采购单位信息接口服务。",
            httpMethod = "POST", response = Tenderer.class, position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tendererId", value = "采购单位id", required = true),
            @ApiImplicitParam(name = "name", value = "单位名称", required = true),
            @ApiImplicitParam(name = "uniformCode", value = "统一社会信用代码"),
            @ApiImplicitParam(name = "contactName", value = "联系人"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "更新成功"),
            @ApiResponse(code=400, message = "角色不符|参数不能为空|单位名称已存在|统一信用代码已存在")})
    @RequestMapping("/tenderer/update")
    public Resp updateTenderer(@RequestParam(defaultValue = "") String tendererId,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String uniformCode,
                               @RequestParam(defaultValue = "") String contactName,
                               @RequestParam(defaultValue = "") String contactPhone) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(tendererId)) return new Resp(400, "采购单位id不能为空");
        if(!StringUtils.isInt(tendererId)) return new Resp(400, "采购单位id无效");
        if(StringUtils.isEmpty(name)) return new Resp(400, "单位名称不能为空");
        // get tenderer
        Tenderer tenderer = tendererRepo.findOne(Integer.valueOf(tendererId));
        if(tenderer == null) return new Resp(400, "无此采购单位");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(!tenderer.getCreatorUid().equals(user.getString("uid"))) return new Resp(400, "你无权修改他人建立的采购单位信息");
        // check name and uniformCode
        Tenderer existName = tendererRepo.findByNameAndAgencyUid(name, agencyUid);
        if(existName != null && !existName.getName().equals(tenderer.getName())) return new Resp(400, "单位名称已存在");
        if(!StringUtils.isEmpty(uniformCode)) {
            Tenderer existUniformCode = tendererRepo.findByUniformCodeAndAgencyUid(uniformCode, agencyUid);
            if(existUniformCode != null && !existUniformCode.getUniformCode().equals(tenderer.getUniformCode())) return new Resp(400, "统一社会信用代码已存在");
        }
        // update Tenderer
        tenderer.setName(name);
        tenderer.setUniformCode(uniformCode);
        tenderer.setContactName(contactName);
        tenderer.setContactPhone(contactPhone);
        tenderer.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderer = tendererRepo.save(tenderer);
        return new Resp(200, "更新成功", tenderer);
    }

    /**
     * 获取采购单位列表
     * @param keyword
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @ApiOperation(value = "获取采购单位列表", notes = "获取所属代理机构下的采购单位列表。",
            httpMethod = "GET", response = Page.class, position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字"),
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页记录数"),
            @ApiImplicitParam(name = "sort", value = "排序值（默认：id,DESC）")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符")})
    @RequestMapping("/tenderer/list")
    public Resp getTendererList(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "20") Integer size,
                               @RequestParam(defaultValue = "id,DESC") String sort) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check and convert params
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
        List<Tenderer> tendererList = tendererRepo.findByAgencyUid(user.getString("corpUid"), mySort);
        // handle keyword search
        if(!StringUtils.isEmpty(keyword)) {
            tendererList = tendererList.stream().filter(t -> t.getName().contains(keyword)).collect(Collectors.toList());
        }
        // prepare page
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>tendererList.size()?tendererList.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>tendererList.size()? tendererList.size():(start + pageable.getPageSize());
        return new Resp(200, "获取成功", new PageImpl<>(tendererList.subList(start,end), pageable, tendererList.size()));
    }

    /**
     * 获取采购单位详情
     * @param tendererId
     * @return
     */
    @ApiOperation(value = "获取采购单位详情", notes = "获取采购单位详情",
            httpMethod = "GET", response = Tenderer.class, position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tendererId", value = "采购单位id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此采购单位")})
    @RequestMapping("/tenderer/detail")
    public Resp getTendererDetail(@RequestParam(defaultValue = "") String tendererId) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(tendererId)) return new Resp(400, "采购单位id不能为空");
        if(!StringUtils.isInt(tendererId)) return new Resp(400, "采购单位id无效");
        // get tenderer
        Tenderer tenderer = tendererRepo.findOne(Integer.valueOf(tendererId));
        if(tenderer == null) return new Resp(400, "无此采购单位");
        return new Resp(200, "获取成功", tenderer);
    }

    /**
     * 删除采购单位
     * @param tendererId
     * @return
     */
    @ApiOperation(value = "删除采购单位", notes = "创建人删除采购单位",
            httpMethod = "POST", response = Resp.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tendererId", value = "采购单位id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此采购单位|无权删除他人的采购单位")})
    @RequestMapping("/tenderer/del")
    public Resp delTenderer(@RequestParam(defaultValue = "") String tendererId) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(tendererId)) return new Resp(400, "采购单位id不能为空");
        if(!StringUtils.isInt(tendererId)) return new Resp(400, "采购单位id无效");
        // get tenderer
        Tenderer tenderer = tendererRepo.findOne(Integer.valueOf(tendererId));
        if(tenderer == null) return new Resp(400, "无此采购单位");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        if(!tenderer.getCreatorUid().equals(user.getString("uid"))) return new Resp(400, "无权删除他人的采购单位");
        tendererRepo.delete(tenderer);
        return new Resp(200, "删除成功");
    }

}
