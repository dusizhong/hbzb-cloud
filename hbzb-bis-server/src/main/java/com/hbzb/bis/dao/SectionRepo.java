package com.hbzb.bis.dao;

import com.hbzb.bis.entity.Section;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 标段数据接口
 * @author dusizhong
 * @since 2020-07-09
 */
public interface SectionRepo extends JpaRepository<Section, Integer> {

    Section findByUid(String uid);
    Section findBySerialNo(String serialNo);
    Section findByProjectUidAndName(String projectUid, String name);
    List<Section> findByProjectUid(String projectUid);
    List<Section> findByAgencyUid(String agencyUid, Sort sort);
    List<Section> findByUidIn(List<String> uids);
}
