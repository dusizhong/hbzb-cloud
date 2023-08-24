package com.hbzb.cloud.tds.dao;

import com.hbzb.cloud.tds.entity.ProjectMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * project material repo
 * created by dusizhong at 2020.01.21
 */
public interface ProjectMaterialRepo extends JpaRepository<ProjectMaterial, Integer> {

    List<ProjectMaterial> findByProjectUid(String projectUid);
}
