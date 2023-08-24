package com.hbzb.bis.dao;

import com.hbzb.bis.entity.BidRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 投标记录数据接口
 * @author dusizhong
 * @since 2020-07-09
 */
public interface BidRecordRepo extends JpaRepository<BidRecord, Integer> {

    BidRecord findBySectionUidAndBidderUid(String sectionUid, String bidderUid);
    List<BidRecord> findBySectionUid(String sectionUid);
}
