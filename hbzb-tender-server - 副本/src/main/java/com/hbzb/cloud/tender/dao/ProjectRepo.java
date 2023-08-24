package com.hbzb.cloud.tender.dao;

import com.hbzb.cloud.tender.entity.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Integer> {
    Project findBySerialNo(String serialNo);
    List<Project> findByCorpUid(String corpUid, Sort sort);
}
