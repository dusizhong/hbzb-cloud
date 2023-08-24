package com.hbzb.tas.dao;

import com.hbzb.tas.entity.BidFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 投标文件数据接口
 * created by dsz at 2020.05.22
 */
public interface BidFileRepo extends JpaRepository<BidFile, Integer> {
    List<BidFile> findBySectionUid(String sectionUid);
}
