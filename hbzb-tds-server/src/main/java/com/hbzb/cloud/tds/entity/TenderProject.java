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
 * 招标项目实例
 * created by dusizhong at 2020.01.23
 */
@ApiModel(value = "招标项目模型")
@Data

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

    @ApiModelProperty(value = "所属项目uid", required = true, position = 3)
    private String projectUid;

    @ApiModelProperty(value = "招标项目编号", required = true, position = 4)
    private String serialNo;

    @ApiModelProperty(value = "招标项目名称", required = true, position = 5)
    private String name;

    @ApiModelProperty(value = "招标内容与范围及招标方案说明", position = 5)
    private String content;
}
