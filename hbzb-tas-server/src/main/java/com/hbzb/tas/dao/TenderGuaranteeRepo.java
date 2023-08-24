package com.hbzb.tas.dao;

import com.hbzb.tas.entity.TenderGuarantee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 投标保证金设置数据接口
 */
public interface TenderGuaranteeRepo extends JpaRepository<TenderGuarantee, Integer> {
    TenderGuarantee findBySectionUid(String sectionUid);
}
