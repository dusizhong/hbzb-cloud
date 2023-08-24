package com.hbzb.tas.dao;

import com.hbzb.tas.entity.EvalCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 评分标准数据接口
 */
public interface EvalCriteriaRepo extends JpaRepository<EvalCriteria, Integer> {

    EvalCriteria findBySectionUidAndCode(String sectionUid, String code);

    List<EvalCriteria> findBySectionUid(String sectionUid);
    List<EvalCriteria> findBySectionUidAndEvalMethodOrderBySortId(String trim, String trim1);

    List<EvalCriteria> findBySectionUidAndParentCode(String sectionUid, String parentCode);
}
