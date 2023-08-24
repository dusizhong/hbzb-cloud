package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * tenderee entity
 * created by dusizhong at 2020.01.23
 */
@ApiModel(value = "1.2 采购单位模型")
@Data
@Entity
public class Tenderee {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "单位名称", required = true, position = 2)
    private String name;

    @ApiModelProperty(value = "统一社会信用代码", required = true, position = 3)
    private String uniformCode;

    @ApiModelProperty(value = "联系人", position = 4)
    private String contactPerson;

    @ApiModelProperty(value = "联系电话", position = 5)
    private String contactPhone;

    @ApiModelProperty(value = "所属招标代理uid", position = 6)
    private String agencyUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 7)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 8)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 9)
    private String createTime;
}
