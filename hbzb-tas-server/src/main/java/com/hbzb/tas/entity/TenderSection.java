package com.hbzb.tas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 招标标段实例
 * created by dusizhong at 2020.05.18
 */
@ApiModel(value = "1.4 招标标段实例")
@Data
@Entity
public class TenderSection {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "项目uid", required = true, position = 2)
    private String projectUid;

    @ApiModelProperty(value = "标段uid", required = true, position = 3)
    private String sectionUid;

    @ApiModelProperty(value = "标段编号", required = true, position = 4)
    private String serialNo;

    @ApiModelProperty(value = "标段名称", required = true, position = 5)
    private String name;

    // 采购方式决定评标方法
    @ApiModelProperty(value = "采购方式（公开招标、邀请招标、竞争性谈判、竞争性磋商、询价、单一来源）", required = true, position = 6)
    private String tradeType;

    // 是否网上采购开评标决定是否设定评标办法、组建评标委员会
    @ApiModelProperty(value = "是否采用网上开评标", required = true, position = 10)
    private String bidOnline;

    @ApiModelProperty(value = "评标方法", position = 12)
    private String evalMethod;

    @ApiModelProperty(value = "开标时间", required = true, position = 11)
    private String bidOpenTime;
    @ApiModelProperty(value = "开标时长（即解密时间）", required = true, position = 11)
    private String bidOpenPeriod;
    @ApiModelProperty(value = "开标场地", position = 12)
    private String bidOpenPlace;

    @ApiModelProperty(value = "评标时间", required = true, position = 11)
    private String bidEvalTime;
    @ApiModelProperty(value = "评标时长（即解密时间）", required = true, position = 11)
    private String bidEvalPeriod;
    @ApiModelProperty(value = "评标场地", position = 12)
    private String bidEvalPlace;

    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 14)
    private String agencyUid;
    @ApiModelProperty(value = "招标代理名称", required = true, position = 15)
    private String agencyName;

    @JsonIgnore
    @ApiModelProperty(value = "搜索关键字", required = true, position = 15)
    private String keyword;

    @ApiModelProperty(value = "状态(EDIT编辑中、SUBMIT待审核、APPROVAL审核通过、REJECT审核不通过)", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "审核人uid", required = true, position = 17)
    private String approverUid;
    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;
    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
