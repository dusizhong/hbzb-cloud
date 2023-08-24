package com.hbzb.tas.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 评标记录实例
 * created by dsz at 2020.05.27
 */
@Data
@Entity
public class EvalRecord {

    @Id
    @GeneratedValue
    private Integer id;
    private String sectionUid;
    private String bidderUid;
    private String parentCode;
    private String criteriaCode;
    private String score;
    private String memo;
    private String creatorUid;
    private String updateTime;
    private String createTime;
}
