package com.hbzb.tas.dao;

import com.hbzb.tas.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 电子件数据接口
 * created by dusizhong at 2020.05.14
 */
public interface MaterialRepo extends JpaRepository<Material, Integer> {
}
