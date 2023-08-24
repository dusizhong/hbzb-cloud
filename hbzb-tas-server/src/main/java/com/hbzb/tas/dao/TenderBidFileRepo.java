package com.hbzb.tas.dao;

import com.hbzb.tas.entity.TenderBidFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 投标文件组成信息数据接口
 */
public interface TenderBidFileRepo extends JpaRepository<TenderBidFile, Integer> {
    List<TenderBidFile> findBySectionUidOrderBySortId(String sectionUid);
}
