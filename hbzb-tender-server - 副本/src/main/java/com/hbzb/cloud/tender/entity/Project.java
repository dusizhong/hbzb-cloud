package com.hbzb.cloud.tender.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "项目实例")
@Data
@Entity
public class Project {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "项目编号", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 100)
    private String serialNo;

    @ApiModelProperty(value = "项目名称", required = true, position = 3)
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @ApiModelProperty(value = "招标人/采购单位", required = true, position = 4)
    private String tenderer;

    @ApiModelProperty(value = "所在辖区", required = true, position = 5)
    private String area;

    @ApiModelProperty(value = "项目地址", position = 6)
    private String address;

    @ApiModelProperty(value = "采购交易分类（货物类、工程类、服务类）", position = 7)
    private String type;

    @ApiModelProperty(value = "行业", position = 8)
    private String industry;

    @ApiModelProperty(value = "资金来源", position = 9)
    private String sourceOfFund;

    @ApiModelProperty(value = "范围", position = 10)
    private String scale;

    @ApiModelProperty(value = "联系人", position = 11)
    private String contactName;

    @ApiModelProperty(value = "联系电话", position = 12)
    private String contactPhone;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 13)
    private String corpUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 14)
    private String creatorUid;

    @ApiModelProperty(value = "状态", required = true, position = 15)
    private String status;

    @ApiModelProperty(value = "更新时间", required = true, position = 16)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 17)
    private String createTime;
}
