package com.hbzb.cloud.tds.entity;

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
 * 项目模型
 * created by dusizhong at 2020.01.15
 */
@ApiModel(value = "1.项目模型")
@Data
@Entity
public class Project {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "项目uid", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    @ApiModelProperty(value = "项目编号", required = true, position = 3)
    private String serialNo;

    @ApiModelProperty(value = "项目名称", required = true, position = 4)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @ApiModelProperty(value = "采购单位", required = true, position = 5)
    private String tenderer;

    @ApiModelProperty(value = "采购联系人", position = 6)
    private String contactName;

    @ApiModelProperty(value = "采购联系电话", position = 7)
    private String contactPhone;

    @ApiModelProperty(value = "所在辖区",  required = true, position = 8)
    private String area;

    @ApiModelProperty(value = "采购人地址", position = 9)
    private String address;

    @ApiModelProperty(value = "招标组织形式（1自行招标、2委托招标、9其他）", required = true, position = 10)
    private String organizeType;

    @ApiModelProperty(value = "采购交易分类（货物类（含药品集中采购）、工程类、服务类）", required = true, position = 10)
    private String tradeCategory;

    @ApiModelProperty(value = "项目内容", position = 11)
    private String content;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 14)
    private String agencyUid;

    @ApiModelProperty(value = "招标代理名称", required = true, position = 14)
    private String agencyName;

    @ApiModelProperty(value = "状态(EDIT编辑中、SUBMIT审核中、APPROVAL审核通过、REJECT已退回)", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
