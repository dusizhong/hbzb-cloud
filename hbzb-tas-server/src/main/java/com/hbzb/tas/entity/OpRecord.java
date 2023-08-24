package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * operation record
 * created by dusizhong at 2020.02.12
 */
@ApiModel(value = "操作记录模型")
@Data
@Entity
public class OpRecord {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "操作项", required = true, position = 2)
    private String itemName;

    @ApiModelProperty(value = "操作项uid", position = 3)
    private String itemUid;

    @ApiModelProperty(value = "操作内容（步骤）", required = true, position = 4)
    private String operate;

    @ApiModelProperty(value = "备注（处理意见）", position = 5)
    private String memo;

    @ApiModelProperty(value = "操作人（办理人员）", position = 6)
    private String creatorName;

    @ApiModelProperty(value = "操作人uid（处理人uid）", required = true, position = 7)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间（处理时间）", required = true, position = 8)
    private String updateTime;

    @ApiModelProperty(value = "创建时间（收到时间）", required = true, position = 9)
    private String createTime;
}
