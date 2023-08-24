package com.hbzb.tas.dao;

import com.hbzb.tas.entity.InviteRecord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 投标邀请记录数据接口
 * @author dusizhong
 * @since 2020-02-07
 */
public interface InviteRecordRepo extends JpaRepository<InviteRecord, Integer> {
    InviteRecord findByUid(String inviteBidderUid);
    List<InviteRecord> findByInvitationUidAndAgencyUid(String invitationUid, String agencyUid, Sort mySort);

    InviteRecord findByInvitationUidAndBidderUniformCode(String trim, String trim1);

    InviteRecord findByInvitationUidAndBidderName(String trim, String trim1);
}
