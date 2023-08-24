package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.TendereeRepo;
import com.hbzb.tas.entity.Tenderee;
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
import java.util.stream.Collectors;


/**
 * Tenderee controller
 * created by dusizhong at 2020.01.23
 */
@Api(tags = "1.2 采购单位接口")
@RestController
public class TendereeController {

    @Autowired
    UaaService uaaService;
    @Autowired
    TendereeRepo tendereeRepo;

    /**
     * 新增采购单位
     * @param name
     * @param uniformCode
     * @param contactPerson
     * @param contactNumber
     * @return
     */
    @ApiOperation(value = "新增采购单位", notes = "新增采购单位接口服务",
            httpMethod = "POST", response = Tenderee.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "单位名称", required = true),
            @ApiImplicitParam(name = "uniformCode", value = "统一社会信用代码", required = true),
            @ApiImplicitParam(name = "contactPerson", value = "联系人"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话")
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "新增成功"),
            @ApiResponse(code=400, message = "角色不符|参数不能为空|单位名称已存在|统一信用代码已存在")})
    @RequestMapping("/Tenderee/create")
    public Resp createTenderee(//@RequestParam(defaultValue = "") String uid,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String uniformCode,
                               @RequestParam(defaultValue = "") String contactPerson,
                               @RequestParam(defaultValue = "") String contactNumber) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(name)) return new Resp("400", "单位名称不能为空");
        if(StringUtils.isEmpty(uniformCode)) return new Resp("400", "统一社会信用代码不能为空");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // check name and uniformCode
        Tenderee existName = tendereeRepo.findByNameAndAgencyUid(name, agencyUid);
        if(existName != null) return new Resp("400", "单位名称已存在");
        Tenderee existUniformCode = tendereeRepo.findByUniformCodeAndAgencyUid(uniformCode, agencyUid);
        if(existUniformCode != null) return new Resp("400", "统一社会信用代码已存在");
        // create Tenderee
        Tenderee Tenderee = new Tenderee();
        Tenderee.setName(name.trim());
        Tenderee.setUniformCode(uniformCode.trim());
        Tenderee.setContactPerson(contactPerson.trim());
        Tenderee.setContactPhone(contactNumber.trim());
        Tenderee.setAgencyUid(agencyUid);
        Tenderee.setCreatorUid(creatorUid);
        Tenderee.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Tenderee.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Tenderee = tendereeRepo.save(Tenderee);
        return new Resp("200", "新增成功", Tenderee);
    }

    /**
     * 更新采购单位
     * @param TendereeId
     * @param name
     * @param uniformCode
     * @param contactPerson
     * @param contactNumber
     * @return
     */
    @ApiOperation(value = "更新采购单位", notes="更新采购单位信息接口服务，仅限创建人更新。",
            httpMethod = "POST", response = Tenderee.class, position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TendereeId", value = "采购单位id", required = true),
            @ApiImplicitParam(name = "name", value = "单位名称", required = true),
            @ApiImplicitParam(name = "uniformCode", value = "统一社会信用代码", required = true),
            @ApiImplicitParam(name = "contactPerson", value = "联系人", required = true),
            @ApiImplicitParam(name = "contactNumber", value = "联系电话", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "更新成功"),
            @ApiResponse(code=400, message = "角色不符|参数不能为空|单位名称已存在|统一信用代码已存在")})
    @RequestMapping("/Tenderee/update")
    public Resp updateTenderee(@RequestParam(defaultValue = "") String TendereeId,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String uniformCode,
                               @RequestParam(defaultValue = "") String contactPerson,
                               @RequestParam(defaultValue = "") String contactNumber) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(TendereeId)) return new Resp("400", "采购单位id不能为空");
        if(!StringUtils.isInt(TendereeId)) return new Resp("400", "采购单位id值无效");
        if(StringUtils.isEmpty(name)) return new Resp("400", "单位名称不能为空");
        if(StringUtils.isEmpty(uniformCode)) return new Resp("400", "统一社会信用代码不能为空");
        if(StringUtils.isEmpty(contactPerson)) return new Resp("400", "联系人不能为空");
        if(StringUtils.isEmpty(contactNumber)) return new Resp("400", "联系电话不能为空");
        // check Tenderee
        Tenderee Tenderee = tendereeRepo.findOne(Integer.valueOf(TendereeId));
        if(Tenderee == null) return new Resp("400", "无此采购单位");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // check creator
        if(!creatorUid.equals(Tenderee.getCreatorUid())) return new Resp("400", "你无权修改他人的采购单位信息");
        // check name and uniformCode
        Tenderee existName = tendereeRepo.findByNameAndAgencyUid(name.trim(), agencyUid);
        if(existName != null && !existName.getName().equals(Tenderee.getName())) return new Resp("400", "单位名称已存在");
        Tenderee existUniformCode = tendereeRepo.findByUniformCodeAndAgencyUid(uniformCode.trim(), agencyUid);
        if(existUniformCode != null && !existUniformCode.getUniformCode().equals(Tenderee.getUniformCode())) return new Resp("400", "统一社会信用代码已存在");
        // update Tenderee
        Tenderee.setName(name.trim());
        Tenderee.setUniformCode(uniformCode.trim());
        Tenderee.setContactPerson(contactPerson.trim());
        Tenderee.setContactPhone(contactNumber.trim());
        Tenderee.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Tenderee = tendereeRepo.save(Tenderee);
        return new Resp("200", "更新成功", Tenderee);
    }

    /**
     * 获取采购单位列表
     * @param name
     * @param uniformCode
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @ApiOperation(value = "获取采购单位列表", notes = "获取采购单位列表接口，仅限代理机构范围内。",
            httpMethod = "GET", response = Page.class, position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "单位名称"),
            @ApiImplicitParam(name = "uniformCode", value = "统一社会信用代码"),
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "size", value = "每页记录数"),
            @ApiImplicitParam(name = "sort", value = "排序值（默认：id,DESC）")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符|排序参数格式错误|排序参数无效")})
    @RequestMapping("/Tenderee/list")
    public Resp getTendereeList(@RequestParam(defaultValue = "") String name,
                                @RequestParam(defaultValue = "") String uniformCode,
                                @RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "20") Integer size,
                                @RequestParam(defaultValue = "id,DESC") String sort) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check and convert sort param
        Sort mySort;
        String[] sorts = sort.split(",");
        if(sorts.length < 2) return new Resp("400", "排序参数格式错误");
        if(sorts[1].equals("ASC")) mySort = new Sort(Sort.Direction.ASC, sorts[0]);
        else if(sorts[1].equals("DESC")) mySort = new Sort(Sort.Direction.DESC, sorts[0]);
        else return new Resp("400", "排序参数值无效");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // get Tenderee list
        List<Tenderee> TendereeList = tendereeRepo.findByAgencyUid(agencyUid, mySort);
        // handle keyword search
        if(!StringUtils.isEmpty(name)) TendereeList = TendereeList.stream().filter(t -> t.getName().contains(name)).collect(Collectors.toList());
        if(!StringUtils.isEmpty(uniformCode)) TendereeList = TendereeList.stream().filter(t -> t.getUniformCode().contains(uniformCode)).collect(Collectors.toList());
        // prepare page
        Pageable pageable = new PageRequest(page, size, mySort);
        int start = pageable.getOffset()>TendereeList.size()?TendereeList.size():pageable.getOffset();
        int end = (start + pageable.getPageSize())>TendereeList.size()? TendereeList.size():(start + pageable.getPageSize());
        return new Resp("200", "获取成功", new PageImpl<>(TendereeList.subList(start,end), pageable, TendereeList.size()));
    }

    /**
     * 获取采购单位详情
     * @param TendereeId
     * @return
     */
    @ApiOperation(value = "获取采购单位详情", notes = "获取采购单位详情接口。",
            httpMethod = "GET", response = Tenderee.class, position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TendereeId", value = "采购单位id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此采购单位")})
    @RequestMapping("/Tenderee/detail")
    public Resp getTendereeDetail(@RequestParam(defaultValue = "") String TendereeId) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(TendereeId)) return new Resp("400", "采购单位id不能为空");
        if(!StringUtils.isInt(TendereeId)) return new Resp("400", "采购单位id值无效");
        // get Tenderee
        Tenderee Tenderee = tendereeRepo.findOne(Integer.valueOf(TendereeId.trim()));
        if(Tenderee == null) return new Resp("400", "无此采购单位");
        return new Resp("200", "获取成功", Tenderee);
    }

    /**
     * 删除采购单位
     * @param TendereeId
     * @return
     */
    @ApiOperation(value = "删除采购单位", notes = "删除采购单位，仅限创建人。",
            httpMethod = "POST", response = Resp.class, position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TendereeId", value = "采购单位id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 400, message = "角色不符|参数不能为空|无此采购单位|无权删除他人的采购单位")})
    @RequestMapping("/Tenderee/del")
    public Resp delTenderee(@RequestParam(defaultValue = "") String TendereeId) {
        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(TendereeId)) return new Resp("400", "采购单位id不能为空");
        if(!StringUtils.isInt(TendereeId.trim())) return new Resp("400", "采购单位id值无效");
        // get Tenderee
        Tenderee Tenderee = tendereeRepo.findOne(Integer.valueOf(TendereeId.trim()));
        if(Tenderee == null) return new Resp("400", "无此采购单位");
        // request user
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(!creatorUid.equals(Tenderee.getCreatorUid())) return new Resp("400", "你无权删除他人的采购单位");
        tendereeRepo.delete(Tenderee);
        return new Resp("200", "删除成功");
    }

}
