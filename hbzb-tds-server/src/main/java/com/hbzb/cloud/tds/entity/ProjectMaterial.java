package com.hbzb.cloud.tds.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "2.项目材料", description = "项目相关电子材料模型描述信息")
@Data
@Entity
public class ProjectMaterial {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "项目uid", required = true, position = 2)
    private String projectUid;

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
