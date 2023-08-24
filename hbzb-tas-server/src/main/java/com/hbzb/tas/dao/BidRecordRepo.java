package com.hbzb.tas.dao;

import com.hbzb.tas.entity.BidRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 投标记录数据接口
 */
public interface BidRecordRepo extends JpaRepository<BidRecord, Integer> {

    BidRecord findBySectionUidAndBidderUid(String sectionUid, String bidderUid);
    List<BidRecord> findBySectionUid(String sectionUid);
}
