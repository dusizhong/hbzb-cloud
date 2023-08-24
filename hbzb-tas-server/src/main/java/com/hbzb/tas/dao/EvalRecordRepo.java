package com.hbzb.tas.dao;

import com.hbzb.tas.entity.EvalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvalRecordRepo extends JpaRepository<EvalRecord, Integer> {

    EvalRecord findBySectionUidAndCriteriaCode(String sectionUid, String criteriaCode);
    List<EvalRecord> findBySectionUidAndParentCode(String sectionUid, String criteriaCode);
}
