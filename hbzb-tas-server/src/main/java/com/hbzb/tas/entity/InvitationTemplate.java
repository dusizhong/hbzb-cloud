package com.hbzb.tas.entity;


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
 * 投标邀请函模板实例
 * created by dsz at 2020.06.29
 */
@ApiModel(value = "4.1 投标邀请模板")
@Data
@Entity
public class InvitationTemplate {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "模板uid", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    @ApiModelProperty(value = "模板内容", required = true, position = 3)
    private String content;

    @ApiModelProperty(value = "状态(正常，停用)", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 14)
    private String agencyUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
