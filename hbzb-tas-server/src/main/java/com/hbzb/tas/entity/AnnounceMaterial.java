package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * announce material entity
 * created by dusizhong at 2020.02.05
 */
@ApiModel(value = "7.公告材料模型", description = "公告材料模型")
@Data
@Entity
public class AnnounceMaterial {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "所属公告uid", required = true, position = 2)
    private String announceUid;

    @ApiModelProperty(value = "材料名称", required = true, position = 3)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @ApiModelProperty(value = "材料电子件", required = true, position = 4)
    private String url;

    @ApiModelProperty(value = "创建人uid", required = true, position = 5)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 6)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 7)
    private String createTime;
}
