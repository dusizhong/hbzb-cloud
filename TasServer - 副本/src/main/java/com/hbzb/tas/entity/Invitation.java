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
 * 投标邀请函实例
 * @author dusizhong
 * @since 2020-02-07
 */
@ApiModel(value = "4.1 投标邀请函模型")
@Data
@Entity
public class Invitation {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "uid", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    @ApiModelProperty(value = "所属标段uids", required = true, position = 3)
    private String sectionUids;

    @ApiModelProperty(value = "名称", required = true, position = 5)
    private String title;

    @ApiModelProperty(value = "回复截至时间", required = true, position = 8)
    private String replyDeadline;

    @ApiModelProperty(value = "邀请函内容", required = true, position = 5)
    private String content;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 14)
    private String agencyUid;

    @ApiModelProperty(value = "状态(EDIT编辑中、SUBMIT审核中、APPROVAL审核通过（已发布）、REJECT已退回)", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "发出时间", required = true, position = 18)
    private String sendTime;

    @ApiModelProperty(value = "审核人（发布人）uid",  position = 17)
    private String approverUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
