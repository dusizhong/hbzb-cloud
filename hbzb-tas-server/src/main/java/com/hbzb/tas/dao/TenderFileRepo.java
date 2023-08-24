package com.hbzb.tas.dao;

import com.hbzb.tas.entity.TenderFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 招标文件数据接口
 * created by dusizhong at 2020.05.07
 */
public interface TenderFileRepo extends JpaRepository<TenderFile, Integer> {
    TenderFile findBySectionUid(String sectionUid);
}
