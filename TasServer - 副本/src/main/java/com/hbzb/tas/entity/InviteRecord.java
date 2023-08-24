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
 * 投标邀请记录实例
 * @author dusizhong
 * @since 2020-02-07
 */
@ApiModel(value = "11.投标邀请记录实例")
@Data
@Entity
public class InviteRecord {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "邀请记录uid", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    @ApiModelProperty(value = "所属邀请函uid", required = true, position = 3)
    private String invitationUid;

    @ApiModelProperty(value = "投标人uid", required = true, position = 3)
    private String bidderUid;

    @ApiModelProperty(value = "投标人名称", required = true, position = 3)
    private String bidderName;

    @ApiModelProperty(value = "投标人统一社会信用代码", required = true, position = 3)
    private String bidderUniformCode;

    @ApiModelProperty(value = "邀请函附件", position = 3)
    private String inviteUrl;

    @ApiModelProperty(value = "邀请发出时间", required = true, position = 18)
    private String inviteTime;

    @ApiModelProperty(value = "回执附件", position = 3)
    private String replyUrl;

    @ApiModelProperty(value = "回复时间", required = true, position = 18)
    private String replyTime;

    @ApiModelProperty(value = "状态(未发出、已发出）", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 17)
    private String agencyUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
