package com.hbzb.tas.dao;

import com.hbzb.tas.entity.Tenderee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * tenderee repo
 * created by dusizhong at 2020.01.23
 */
public interface TendereeRepo extends JpaRepository<Tenderee, Integer> {

    Tenderee findByNameAndAgencyUid(String name, String agencyUid);
    Tenderee findByUniformCodeAndAgencyUid(String uniformCode, String agencyUid);
    List<Tenderee> findByAgencyUid(String agencyUid, Sort sort);
}
