package com.hbzb.tas.dao;

import com.hbzb.tas.entity.TenderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenderSheetRepo extends JpaRepository<TenderSheet, Integer> {
    List<TenderSheet> findBySectionUid(String sectionUid);
}
