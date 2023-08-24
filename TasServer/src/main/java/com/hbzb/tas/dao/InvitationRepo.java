package com.hbzb.tas.dao;

import com.hbzb.tas.entity.Invitation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 邀请函数据接口
 * created by dusizhong at 2020.02.07
 */
public interface InvitationRepo extends JpaRepository<Invitation, Integer> {
    Invitation findByUid(String invitationUid);
    List<Invitation> findByAgencyUid(String agencyUid, Sort mySort);
}
