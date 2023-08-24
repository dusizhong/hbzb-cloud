package com.hbzb.tas.dao;

import com.hbzb.tas.entity.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * project repository
 * created by dusizhong at 2020.01.15
 */
public interface ProjectRepo extends JpaRepository<Project, Integer> {
    Project findByUid(String uid);
    Project findBySerialNo(String serialNo);
    Project findByName(String name);
    List<Project> findByAgencyUid(String agencyUid, Sort sort);
}
