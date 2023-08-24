package com.hbzb.tas.dao;

import com.hbzb.tas.entity.ProjectMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 项目材料数据接口
 * created by dusizhong at 2020.01.21
 */
public interface ProjectMaterialRepo extends JpaRepository<ProjectMaterial, Integer> {

    ProjectMaterial findByProjectUidAndName(String projectUid, String name);
    List<ProjectMaterial> findByProjectUid(String projectUid);
}
