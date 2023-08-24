package com.hbzb.tas.dao;

import com.hbzb.tas.entity.TenderSection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 招标标段数据接口
 * created by dusizhong at 2020.05.18
 */
public interface TenderSectionRepo extends JpaRepository<TenderSection, Integer> {

    TenderSection findBySectionUid(String sectionUid);
    List<TenderSection> findByAgencyUid(String agencyUid, Sort mySort);

    List<TenderSection> findBySectionUidIn(List<String> sectionUids);
}
