package com.hbzb.tas.dao;

import com.hbzb.tas.entity.AnnounceMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * announce material repository
 * created by dusizhong at 2020.02.05
 */
public interface AnnounceMaterialRepo extends JpaRepository<AnnounceMaterial, Integer> {
    List<AnnounceMaterial> findByAnnounceUid(String announceUid);
}
