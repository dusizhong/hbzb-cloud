package com.hbzb.tas.dao;

import com.hbzb.tas.entity.EvalCriteriaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 评分标准模板数据接口
 */
public interface EvalCriteriaTemplateRepo extends JpaRepository<EvalCriteriaTemplate, Integer> {
    List<EvalCriteriaTemplate> findByEvalMethodOrderBySortId(String evalMethod);
}
