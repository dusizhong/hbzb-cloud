package com.hbzb.tas.dao;

import com.hbzb.tas.entity.EvalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 开评标成员（含评标委员会）数据接口
 */
public interface EvalMemberRepo extends JpaRepository<EvalMember, Integer> {

    EvalMember findBySectionUidAndUserUid(String sectionUid, String userUid);
    List<EvalMember> findBySectionUidOrderBySortId(String sectionId);
}
