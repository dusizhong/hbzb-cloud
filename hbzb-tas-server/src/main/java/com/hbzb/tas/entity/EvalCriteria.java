package com.hbzb.tas.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 评分标准实例
 */
@Data
@Entity
public class EvalCriteria {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer sortId;
    private String sectionUid;
    private String evalMethod;
    private String parentCode;
    private String name;
    private String code;
    private String content;
    private String scoreType;
    private String totalScore;
    private String minScore;
    private String maxScore;
    private String basicFormula;
    private String deductFormula;
    private String toPage;
    private String creatorUid;
    private String updateTime;
    private String createTime;
}
