package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.*;
import com.hbzb.tas.entity.*;
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

@RestController
public class EvalRecordController {

    @Autowired
    UaaService uaaService;
    @Autowired
    TenderSectionRepo tenderSectionRepo;
    @Autowired
    BidRecordRepo bidRecordRepo;
    @Autowired
    EvalCriteriaRepo evalCriteriaRepo;
    @Autowired
    EvalRecordRepo evalRecordRepo;
    @Autowired
    EvalMemberRepo evalMemberRepo;


    @RequestMapping("/eval/record/create")
    public Resp createEvalRecord(@RequestParam(defaultValue = "") String sectionUid,
                                 @RequestParam(defaultValue = "") String bidderUid,
                                 @RequestParam(defaultValue = "") String criteriaCode,
                                 @RequestParam(defaultValue = "") String score,
                                 @RequestParam(defaultValue = "") String memo) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY") && !authorities.contains("ROLE_EXPERT")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(criteriaCode)) return new Resp("400", "评分标准代码不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String userUid = user.getString("uid");
        String userName = user.getString("username");
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // 检查招标标段
        TenderSection tenderSection = tenderSectionRepo.findBySectionUid(sectionUid.trim());
        if(tenderSection == null) return new Resp("400", "无此招标标段");
        // 检查访问权限？
        if(authorities.contains("ROLE_AGENCY")) {
            if (!agencyUid.equals(tenderSection.getAgencyUid())) return new Resp("400", "无权查看其他代理招标标段信息");
        }
        if(authorities.contains("ROLE_EXPERT")) {
            EvalMember member = evalMemberRepo.findBySectionUidAndUserUid(sectionUid.trim(), userUid);
            if (member == null) return new Resp("400", "您不是此标段成员，无权评审此招标标段");
        }
        // 创建/修改
        BidRecord bidRecord = bidRecordRepo.findBySectionUidAndBidderUid(sectionUid.trim(), bidderUid.trim());
        if (bidRecord == null) return new Resp("400", "此标段无此投标人");
        EvalCriteria evalCriteria = evalCriteriaRepo.findBySectionUidAndCode(sectionUid.trim(), criteriaCode.trim());
        if(evalCriteria == null) return new Resp("400", "此标段无此评分项");
        EvalRecord evalRecord = evalRecordRepo.findBySectionUidAndCriteriaCode(sectionUid.trim(), criteriaCode.trim());
        if(evalRecord == null) {
            evalRecord = new EvalRecord();
            evalRecord.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        evalRecord.setBidderUid(bidderUid.trim());
        evalRecord.setSectionUid(sectionUid.trim());
        evalRecord.setParentCode(evalCriteria.getParentCode());
        evalRecord.setCriteriaCode(criteriaCode.trim());
        evalRecord.setScore(score.trim());
        evalRecord.setMemo(memo.trim());
        evalRecord.setCreatorUid(userUid);
        evalRecord.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        evalRecord = evalRecordRepo.save(evalRecord);
        return new Resp("200", "操作成功", evalRecord);
    }


    @RequestMapping("/eval/record/list")
    public Resp getEvalRecordList(@RequestParam(defaultValue = "") String sectionUid,
                                  @RequestParam(defaultValue = "") String parentCode) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(parentCode)) return new Resp("400", "评分标准代码不能为空");
        // 获取用户信息
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject)JSONObject.toJSON(jsonUser.get("data"));
        String agencyUid = user.getString("corpUid");
        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取用户corpUid失败");
        // 检查招标标段
        TenderSection tenderSection = tenderSectionRepo.findBySectionUid(sectionUid.trim());
        if(tenderSection == null) return new Resp("400", "无此招标标段");
        if(!agencyUid.equals(tenderSection.getAgencyUid())) return new Resp("400", "无权查看其他代理招标标段信息");
        // 筛选数据
        List<BidRecord> bidRecords = bidRecordRepo.findBySectionUid(sectionUid.trim());

        List<EvalCriteria> evalCriterias = evalCriteriaRepo.findBySectionUidAndParentCode(sectionUid.trim(), parentCode.trim());

        List<EvalRecord> evalRecords = evalRecordRepo.findBySectionUidAndParentCode(sectionUid.trim(), parentCode.trim());

        JSONObject result = new JSONObject();
        // 组装表头
//        JSONArray tableCol = new JSONArray();
//        JSONObject object = new JSONObject();
//        object.put("prop", "bidderName");
//        object.put("label", "投标人");
        result.put("tableCols", evalCriterias);

        // 组装数据
        JSONArray array = new JSONArray();
        for(BidRecord bidRecord : bidRecords) {
            JSONObject object = new JSONObject();
            object.put("bidderUid", bidRecord.getBidderUid());
            object.put("bidderName", bidRecord.getBidderName());
            for(EvalCriteria criteria : evalCriterias) {
                object.put(criteria.getCode(), "");
                for (EvalRecord evalRecord : evalRecords) {
                    if(evalRecord.getBidderUid().equals(bidRecord.getBidderUid())) {
                        if (criteria.getCode().equals(evalRecord.getCriteriaCode())) {
                            object.put(criteria.getCode(), evalRecord.getScore());
                        }
                    }
                }
            }
            object.put("result", "");
            array.add(object);
        }
        result.put("tableData", array);

        return new Resp("200", "获取成功", result);
    }
}
