package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * operate record entity
 * created by dusizhong at 2020.02.12
 */
@ApiModel("处理历史模型")
@Data
@Entity
public class HandleRecord {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "处理项", required = true, position = 2)
    private String itemName;

    @ApiModelProperty(value = "处理项uid", position = 3)
    private String itemUid;

    @ApiModelProperty(value = "步骤", required = true, position = 4)
    private String step;

    @ApiModelProperty(value = "处理意见", position = 5)
    private String opinion;

    @ApiModelProperty(value = "办理人员", position = 6)
    private String handlerName;

    @ApiModelProperty(value = "办理人员uid", required = true, position = 7)
    private String handlerUid;

    @ApiModelProperty(value = "收到时间", required = true, position = 8)
    private String receiveTime;

    @ApiModelProperty(value = "处理时间", position = 9)
    private String handleTime;

    @ApiModelProperty(value = "创建人姓名", position = 10)
    private String creatorName;

    @ApiModelProperty(value = "创建人uid", required = true, position = 11)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 12)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 13)
    private String createTime;
}
