package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 开标一览表实例
 * created by dusizhong at 2020.05.09
 */
@ApiModel(value = "开标一览表实例")
@Data
@Entity
public class TenderSheet {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "标段uid", required = true, position = 3)
    private String sectionUid;

    @ApiModelProperty(value = "数据项", required = true, position = 3)
    private String item;

    @ApiModelProperty(value = "数据类型（数字、字符串)", required = true, position = 11)
    private String type;

    @ApiModelProperty(value = "单位", position = 11)
    private String unit;

    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
