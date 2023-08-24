package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ApiModel(value = "投标文件组成实例")
@Data
@Entity
public class TenderBidFile {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "序号", required = true, position = 2)
    private Integer sortId;

    @ApiModelProperty(value = "标段（包）uid", required = true, position = 2)
    private String sectionUid;

    @ApiModelProperty(value = "文件名称", required = true, position = 3)
    private String name;

    @ApiModelProperty(value = "说明", position = 11)
    private String note;

    @ApiModelProperty(value = "是否必须", position = 11)
    private String needed;

    @ApiModelProperty(value = "是否签章", position = 5)
    private String sealed;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
