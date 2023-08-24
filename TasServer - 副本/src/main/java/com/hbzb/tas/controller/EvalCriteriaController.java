package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.EvalCriteriaRepo;
import com.hbzb.tas.dao.EvalCriteriaTemplateRepo;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.entity.EvalCriteria;
import com.hbzb.tas.entity.EvalCriteriaTemplate;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评分标准设置接口
 */
@RestController
public class EvalCriteriaController {

    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    EvalCriteriaRepo evalCriteriaRepo;
    @Autowired
    EvalCriteriaTemplateRepo evalCriteriaTemplateRepo;


    @RequestMapping("/eval/criteria/init")
    public Resp init(@RequestParam(defaultValue = "") String sectionUid,
                     @RequestParam(defaultValue = "") String evalMethod) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(evalMethod)) return new Resp("400", "评标方法不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 删除原评分点
        List<EvalCriteria> oldEvalCriterias = evalCriteriaRepo.findBySectionUid(sectionUid.trim());
        evalCriteriaRepo.delete(oldEvalCriterias);
        // 初始化新评分点
        List<EvalCriteriaTemplate> evalCriteriaTemplates = evalCriteriaTemplateRepo.findByEvalMethodOrderBySortId(evalMethod.trim());
        List<EvalCriteria> evalCriterias = new ArrayList<>();
        for(EvalCriteriaTemplate template : evalCriteriaTemplates) {
            EvalCriteria evalCriteria = new EvalCriteria();
            evalCriteria.setSortId(template.getSortId());
            evalCriteria.setSectionUid(sectionUid.trim());
            evalCriteria.setEvalMethod(template.getEvalMethod());
            evalCriteria.setParentCode(template.getParentCode());
            evalCriteria.setName(template.getName());
            evalCriteria.setCode(template.getCode());
            evalCriteria.setContent(template.getContent());
            evalCriteria.setScoreType(template.getScoreType());
            evalCriteria.setMinScore(template.getMinScore());
            evalCriteria.setMaxScore(template.getMaxScore());
            evalCriteria.setTotalScore(template.getTotalScore());
            evalCriteria.setBasicFormula(template.getBaseFormula());
            evalCriteria.setDeductFormula(template.getDeductFormula());
            evalCriteria.setCreatorUid(creatorUid);
            evalCriteria.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            evalCriteria.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            evalCriterias.add(evalCriteria);
        }
        evalCriteriaRepo.save(evalCriterias);
        // 更新评标办法
        section.setEvalMethod(evalMethod.trim());
        sectionRepo.save(section);
        return new Resp("200", "操作成功", section);
    }

//    @RequestMapping("/eval/criteria/template/list")
//    public Resp getTemplatelist(@RequestParam(defaultValue = "") String tenderSectionUid,
//                        @RequestParam(defaultValue = "") String evalMethod) {
//
//        // check authorities
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String authorities = auth.getAuthorities().toString();
//        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
//        // check params
//        if(StringUtils.isEmpty(tenderSectionUid)) return new Resp("400", "标段（包）uid不能为空");
//        if(StringUtils.isEmpty(evalMethod)) return new Resp("400", "评标方法不能为空");
//
//        List<EvalCriteriaTemplate> evalCriteriaTemplates = evalCriteriaTemplateRepo.findByEvalMethodOrderBySortId(evalMethod.trim());
//        // construct
//        Integer id = 0;
//        JSONArray jsonArray = new JSONArray();
//        for(EvalCriteriaTemplate template : evalCriteriaTemplates) {
//            if(template.getParentId().equals(0)) {
//                JSONObject parent = new JSONObject();
//                parent.put("name", template.getName());
//                JSONArray children = new JSONArray();
//                for(EvalCriteriaTemplate template1 : evalCriteriaTemplates) {
//                    if(template1.getParentId().equals(template.getId())) {
//                        JSONObject child = new JSONObject();
//                        child.put("id", id);
//                        child.put("name", template1.getName());
//                        JSONArray criterias = new JSONArray();
//                        for(EvalCriteriaTemplate template2 : evalCriteriaTemplates) {
//                            if(template2.getParentId().equals(template1.getId())) {
//                                JSONObject criteria = (JSONObject) JSONObject.toJSON(template2);
//                                criterias.add(criteria);
//                            }
//                        }
//                        child.put("criterias", criterias);
//                        children.add(child);
//                        id++;
//                    }
//                }
//                parent.put("children", children);
//                jsonArray.add(parent);
//            }
//        }
//        return new Resp("200", "操作成功", jsonArray);
//    }


    @RequestMapping("/eval/criteria/create")
    public Resp createEvalCriteria(@RequestParam(defaultValue = "") String id,
                                   @RequestParam(defaultValue = "") String sortId,
                                   @RequestParam(defaultValue = "") String sectionUid,
                                   @RequestParam(defaultValue = "") String evalMethod,
                                   @RequestParam(defaultValue = "") String parentCode,
                                   @RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "") String content,
                                   @RequestParam(defaultValue = "") String scoreType,
                                   @RequestParam(defaultValue = "") String minScore,
                                   @RequestParam(defaultValue = "") String maxScore,
                                   @RequestParam(defaultValue = "") String totalScore,
                                   @RequestParam(defaultValue = "") String basicFormula,
                                   @RequestParam(defaultValue = "") String deductFormula,
                                   @RequestParam(defaultValue = "") String toPage) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sortId)) return new Resp("400", "序号不能为空");
        if(!StringUtils.isInt(sortId)) return new Resp("400", "序号格式无效");
        if(StringUtils.isEmpty(parentCode)) return new Resp("400", "父类编码不能为空");
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(evalMethod)) return new Resp("400", "所属评分方法不能为空");
        if(StringUtils.isEmpty(name)) return new Resp("400", "评分点名称不能为空");
        if(StringUtils.isEmpty(scoreType)) return new Resp("400", "打分方式不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 新增/更新
        EvalCriteria evalCriteria;
        if(StringUtils.isEmpty(id)) {
            evalCriteria = new EvalCriteria();
            evalCriteria.setCode(System.currentTimeMillis()+"");
            evalCriteria.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            if(!StringUtils.isInt(id)) return new Resp("400", "id格式有误");
            evalCriteria = evalCriteriaRepo.findOne(Integer.valueOf(id.trim()));
            if(evalCriteria == null) return new Resp("400", "无此评分点信息");
        }
        evalCriteria.setSortId(Integer.valueOf(sortId.trim()));
        evalCriteria.setSectionUid(sectionUid.trim());
        evalCriteria.setEvalMethod(evalMethod.trim());
        evalCriteria.setParentCode(parentCode.trim());
        evalCriteria.setName(name.trim());
        evalCriteria.setContent(content.trim());
        evalCriteria.setScoreType(scoreType.trim());
        evalCriteria.setMinScore(minScore.trim());
        evalCriteria.setMaxScore(maxScore.trim());
        evalCriteria.setTotalScore(totalScore.trim());
        evalCriteria.setBasicFormula(basicFormula.trim());
        evalCriteria.setDeductFormula(deductFormula.trim());
        evalCriteria.setToPage(toPage.trim());
        evalCriteria.setCreatorUid(creatorUid);
        evalCriteria.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        evalCriteria = evalCriteriaRepo.save(evalCriteria);
        return new Resp("200", "操作成功", evalCriteria);
    }


    @RequestMapping("/eval/criteria/list")
    public Resp getCriteralist(@RequestParam(defaultValue = "") String sectionUid,
                               @RequestParam(defaultValue = "") String evalMethod) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(evalMethod)) return new Resp("400", "评标方法不能为空");
        // 获取数据
        List<EvalCriteria> criterias = evalCriteriaRepo.findBySectionUidAndEvalMethodOrderBySortId(sectionUid.trim(), evalMethod.trim());
        // 组装数据
        JSONArray jsonArray = new JSONArray();
        for(EvalCriteria criteria : criterias) {
            if(StringUtils.isEmpty(criteria.getParentCode())) {
                JSONObject parent = new JSONObject();
                parent.put("name", criteria.getName());
                JSONArray children = new JSONArray();
                for(EvalCriteria criteria1 : criterias) {
                    if(criteria1.getParentCode().equals(criteria.getCode())) {
                        JSONObject child = new JSONObject();
                        child.put("code", criteria1.getCode());
                        child.put("name", criteria1.getName());
                        JSONArray data = new JSONArray();
                        for(EvalCriteria criteria2 : criterias) {
                            if(criteria2.getParentCode().equals(criteria1.getCode())) {
                                JSONObject json = (JSONObject)JSONObject.toJSON(criteria2);
                                data.add(json);
                            }
                        }
                        child.put("data", data);
                        children.add(child);
                    }
                }
                parent.put("children", children);
                jsonArray.add(parent);
            }
        }
        return new Resp("200", "操作成功", jsonArray);
    }

    @RequestMapping("/eval/criteria/del")
    public Resp delCriteria(@RequestParam(defaultValue = "") String id) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(id)) return new Resp("400", "评分点id不能为空");
        if(!StringUtils.isInt(id)) return new Resp("400", "id格式错误");
        // 获取数据
        EvalCriteria evalCriteria = evalCriteriaRepo.findOne(Integer.valueOf(id.trim()));
        if(evalCriteria == null) return new Resp("400", "无此评分点");
        // 检查标段
        Section section = sectionRepo.findByUid(evalCriteria.getSectionUid());
        if(section == null) return new Resp("400", "数据异常！该投标文件组成项所属招标信息不存在");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态状态不在编辑中");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(!creatorUid.equals(evalCriteria.getCreatorUid())) return new Resp("400", "此评分点非你创建，无权删除");
        // 删除
        evalCriteriaRepo.delete(evalCriteria);
        return new Resp("200", "删除成功", evalCriteria);
    }
}
