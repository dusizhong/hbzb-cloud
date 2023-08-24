package com.hbzb.tas.service;

import com.hbzb.tas.dao.OpRecordRepo;
import com.hbzb.tas.entity.OpRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * operation record service
 * created by dusizhong at 2020.02.12
 */
@Service
public class OpRecordService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OpRecordRepo opRecordRepo;

    public void createRecord(String itemName, String itemUid, String operate, String memo, String creatorUid, String creatorName) {

        OpRecord opRecord = new OpRecord();
        opRecord.setItemName(itemName);
        opRecord.setItemUid(itemUid);
        opRecord.setOperate(operate);
        opRecord.setMemo(memo);
        opRecord.setCreatorName(creatorName);
        opRecord.setCreatorUid(creatorUid);
        opRecord.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        opRecord.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        opRecordRepo.save(opRecord);
    }
}
