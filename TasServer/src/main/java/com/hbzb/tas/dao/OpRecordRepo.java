package com.hbzb.tas.dao;

import com.hbzb.tas.entity.OpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * operate record repository
 * create by dusizhong at 2020.02.12
 */
public interface OpRecordRepo extends JpaRepository<OpRecord, Integer> {
    List<OpRecord> findByItemUid(String itemUid);
}
