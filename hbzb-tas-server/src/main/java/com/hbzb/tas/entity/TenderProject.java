package com.hbzb.tas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 招标项目实例
 * created by dsz at 2020.06.17
 */
@ApiModel(value = "1.1 招标项目实例")
@Data
@Entity
public class TenderProject {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "招标项目uid", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    // 招标项目编号（招标项目编号20位）
    // 1位行业门类字母码 + 6位行政区域代码 + 4位全国交易平台序列号 + 项目序列号的取值从000001-999999
    // "E" + "130100" + "1001" + "XXXXXX"
    @ApiModelProperty(value = "招标项目编号", required = true, position = 3)
    private String code;

    @ApiModelProperty(value = "招标项目名称", required = true, position = 4)
    private String name;

    @ApiModelProperty(value = "采购单位uid", required = true, position = 5)
    private String tendereeUid;

    @ApiModelProperty(value = "采购单位名称", required = true, position = 5)
    private String tendereeName;

    @ApiModelProperty(value = "采购联系人", position = 7)
    private String contactPerson;

    @ApiModelProperty(value = "采购联系电话", position = 8)
    private String contactPhone;

    @ApiModelProperty(value = "采购地址", position = 9)
    private String address;

    @ApiModelProperty(value = "所在辖区", position = 8)
    private String area;

    @ApiModelProperty(value = "采购方式（公开招标、邀请招标、竞争性谈判、竞争性磋商、询价、单一来源）", required = true, position = 7)
    private String tradeType;

    @ApiModelProperty(value = "采购交易分类（工程类、服务类、货物类（含药品集中采购））", position = 8)
    private String tradeCategory;

    @ApiModelProperty(value = "交易组织形式（自主招标、社会代理、采购中心、其他）", required = true, position = 10)
    private String organizeType;

    @ApiModelProperty(value = "投资项目统一代码（代码长度：24位，代码格式：年份代码-地区（部门）代码-行业代码-项目类型代码-流水号）", position = 11)
    private String investmentCode;

    @ApiModelProperty(value = "投资主体性质（政府投资、企业投资、其他）", position = 11)
    private String investorType;

    @ApiModelProperty(value = "是否PPP项目", position = 11)
    @Column(name = "is_PPP")
    private String isPPP;

    @ApiModelProperty(value = "行政监督部门", position = 14)
    private String supervisor;

    @ApiModelProperty(value = "所属公共资源", position = 14)
    private String resourcer;

    @ApiModelProperty(value = "推送平台", position = 8)
    private String platform;

    @ApiModelProperty(value = "采购内容与范围及招标方案说明", position = 6)
    private String content;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 9)
    private String agencyUid;

    @ApiModelProperty(value = "招标代理名称", required = true, position = 10)
    private String agencyName;

    @JsonIgnore
    @ApiModelProperty(value = "搜索关键字", position = 15)
    private String keyword;

    @ApiModelProperty(value = "状态(EDIT编辑中、SUBMIT待审核、APPROVAL审核通过、REJECT审核不通过)", required = true, position = 11)
    private String status;

    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    @ApiModelProperty(value = "审批人uid", position = 12)
    private String approverUid;

    @ApiModelProperty(value = "创建人（申报责任人）", required = true, position = 13)
    private String creatorName;

    @ApiModelProperty(value = "创建人uid", required = true, position = 14)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 15)
    private String updateTime;

    @ApiModelProperty(value = "创建时间（建立时间）", required = true, position = 16)
    private String createTime;
}
