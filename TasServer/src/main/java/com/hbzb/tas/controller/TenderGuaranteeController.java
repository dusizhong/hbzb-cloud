package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.dao.TenderGuaranteeRepo;
import com.hbzb.tas.dao.TenderSectionRepo;
import com.hbzb.tas.entity.Section;
import com.hbzb.tas.entity.TenderGuarantee;
import com.hbzb.tas.entity.TenderSection;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.OpRecordService;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 投标保证金设置接口
 */
@RestController
public class TenderGuaranteeController {

    @Autowired
    UaaService uaaService;
    @Autowired
    OpRecordService opRecordService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    TenderGuaranteeRepo tenderGuaranteeRepo;


    @ApiOperation(value = "新增/更新招标保证金设置", httpMethod = "POST", response = TenderGuarantee.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionUid", value = "标段uid", required = true),
            @ApiImplicitParam(name = "amount", value = "保证金金额（元）", required = true),
            @ApiImplicitParam(name = "deadline", value = "保证金缴纳截止时间", required = true),
            @ApiImplicitParam(name = "type", value = "缴纳方式（现金CASH、银行保函BANK_GUARANTEE、担保机构保函CORP_GUARANTEE）", required = true),
            @ApiImplicitParam(name = "bankName", value = "保证金缴纳开户行"),
            @ApiImplicitParam(name = "accountName", value = "保证金缴纳户名"),
            @ApiImplicitParam(name = "accountNo", value = "保证金缴纳账号"),
            @ApiImplicitParam(name = "basicPay", value = "必须基本户支付", required = true),
            @ApiImplicitParam(name = "memo", value = "备注")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增/更新成功"),
            @ApiResponse(code = 400, message = "无权访问|参数不能为空|")})
    @RequestMapping("/tender/guarantee/create")
    public Resp createTenderGuarantee(@RequestParam(defaultValue = "") String sectionUid,
                                      @RequestParam(defaultValue = "") String amount,
                                      @RequestParam(defaultValue = "") String deadline,
                                      @RequestParam(defaultValue = "") String type,
                                      @RequestParam(defaultValue = "") String bankName,
                                      @RequestParam(defaultValue = "") String accountName,
                                      @RequestParam(defaultValue = "") String accountNo,
                                      @RequestParam(defaultValue = "") String basicPay,
                                      @RequestParam(defaultValue = "") String memo) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(amount)) return new Resp("400", "保证金金额不能为空");
        if(!StringUtils.isAmount(amount)) return new Resp("400", "保证金金额格式无效");
        if(StringUtils.isEmpty(deadline)) return new Resp("400", "保证金缴纳截止时间不能为空");
        if(StringUtils.isEmpty(type)) return new Resp("400", "缴纳方式不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        //检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中，不能操作");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 新增/修改保证金设置
        TenderGuarantee tenderGuarantee = tenderGuaranteeRepo.findBySectionUid(sectionUid.trim());
        if(tenderGuarantee == null) {
            tenderGuarantee = new TenderGuarantee();
            tenderGuarantee.setSectionUid(sectionUid.trim());
            tenderGuarantee.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        tenderGuarantee.setAmount(amount.trim());
        tenderGuarantee.setDeadline(deadline.trim());
        tenderGuarantee.setType(type.trim());
        tenderGuarantee.setBankName(bankName.trim());
        tenderGuarantee.setAccountName(accountName.trim());
        tenderGuarantee.setAccountNo(accountNo.trim());
        tenderGuarantee.setBasicPay(basicPay.trim());
        tenderGuarantee.setMemo(memo.trim());
        tenderGuarantee.setCreatorUid(creatorUid);
        tenderGuarantee.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderGuarantee = tenderGuaranteeRepo.saveAndFlush(tenderGuarantee);
        return new Resp("200", "操作成功", tenderGuarantee);
    }


    @RequestMapping("/tender/guarantee/detail")
    public Resp getTenderGuaranteeDetail(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取保证金设置
        TenderGuarantee tenderGuarantee = tenderGuaranteeRepo.findBySectionUid(sectionUid.trim());
        if(tenderGuarantee == null) return new Resp("400", "保证金信息不存在");
        return new Resp("200", "获取成功", tenderGuarantee);
    }
}
