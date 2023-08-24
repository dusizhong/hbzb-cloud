package com.hbzb.tas.dao;

import com.hbzb.tas.entity.Announce;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * announce repository
 * created by dusizhong at 2020.02.05
 */
public interface AnnounceRepo extends JpaRepository<Announce, Integer> {
    Announce findByUid(String announceUid);
    List<Announce> findByAgencyUid(String agencyUid, Sort mySort);
}
