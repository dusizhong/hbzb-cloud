package com.hbzb.tas.dao;

import com.hbzb.tas.entity.InvitationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 投标邀请函模板数据接口
 * created by dusizhong at 2020.06.29
 */
public interface InvitationTemplateRepo extends JpaRepository<InvitationTemplate, Integer> {
    InvitationTemplate findByAgencyUid(String agencyUid);
}
