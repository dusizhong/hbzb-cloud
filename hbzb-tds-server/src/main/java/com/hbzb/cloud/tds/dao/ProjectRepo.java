package com.hbzb.cloud.tds.dao;

import com.hbzb.cloud.tds.entity.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Integer> {
    Project findByUid(String uid);
    Project findBySerialNo(String serialNo);
    Project findFirstByOrderByIdDesc();
    List<Project> findByCorpUid(String corpUid, Sort sort);
}
