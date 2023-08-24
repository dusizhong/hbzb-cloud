package com.hbzb.tas.dao;

import com.hbzb.tas.entity.ApprovalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * approval record repo
 * created by dusizhong at 2020.01.21
 */
public interface ApprovalRecordRepo extends JpaRepository<ApprovalRecord, Integer> {
}
