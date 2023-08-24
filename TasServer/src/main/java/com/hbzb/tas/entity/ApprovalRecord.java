package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ApiModel(value = "审核记录模型", description = "项目、招标项目、场地、公告等审核记录模型")
@Data
@Entity
public class ApprovalRecord {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "审核项（项目、招标项目、场地、公告）", required = true, position = 2)
    private String itemType;

    @ApiModelProperty(value = "审核项uid", required = true, position = 3)
    private String itemUid;

    @ApiModelProperty(value = "步骤", required = true, position = 4)
    private String step;

    @ApiModelProperty(value = "处理意见", required = true, position = 5)
    private String opinion;

    @ApiModelProperty(value = "处理人姓名", required = true, position = 6)
    private String creatorName;

    @ApiModelProperty(value = "处理人uid", required = true, position = 6)
    private String creatorUid;

    @ApiModelProperty(value = "创建时间", required = true, position = 7)
    private String createTime;
}
