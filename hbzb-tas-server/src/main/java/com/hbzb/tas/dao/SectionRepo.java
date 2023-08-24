package com.hbzb.tas.dao;

import com.hbzb.tas.entity.Section;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 标段数据接口
 * created by dusizhong at 2020.06.29
 */
public interface SectionRepo extends JpaRepository<Section, Integer> {

    Section findByUid(String uid);
    Section findBySerialNo(String serialNo);
    Section findByProjectUidAndName(String projectUid, String name);
    List<Section> findByProjectUid(String projectUid);
    List<Section> findByAgencyUid(String agencyUid, Sort sort);
    List<Section> findByUidIn(List<String> uids);
}
