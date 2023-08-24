package com.hbzb.cloud.tds.dao;

import com.hbzb.cloud.tds.entity.Tenderer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * tenderer repo
 * created by dusizhong at 2020.01.23
 */
public interface TendererRepo extends JpaRepository<Tenderer, Integer> {

    Tenderer findByNameAndAgencyUid(String name, String agencyUid);
    Tenderer findByUniformCodeAndAgencyUid(String uniformCode, String agencyUid);
    List<Tenderer> findByAgencyUid(String agencyUid, Sort sort);
}
