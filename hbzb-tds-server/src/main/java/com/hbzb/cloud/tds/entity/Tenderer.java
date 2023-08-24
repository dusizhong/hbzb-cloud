package com.hbzb.cloud.tds.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 采购单位模型
 * 新增项目时由招标代理录入，便于重复使用，不做信息核验
 * 仅招标代理为单位可见
 * 采购单位不参与招标流程，暂不按照平台用户注册
 * created by dusizhong at 2020.01.23
 */
@ApiModel(value = "采购单位模型")
@Data
@Entity
public class Tenderer {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "单位名称", required = true, position = 2)
    private String name;

    @ApiModelProperty(value = "统一社会信用代码", position = 3)
    private String uniformCode;

    @ApiModelProperty(value = "联系人", position = 4)
    private String contactName;

    @ApiModelProperty(value = "联系电话", position = 5)
    private String contactPhone;

    @ApiModelProperty(value = "所属招标代理uid", required = true, position = 6)
    private String agencyUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 7)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 8)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 9)
    private String createTime;
}
