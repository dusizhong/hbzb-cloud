package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 开评标成员实例（含评标委员会）
 * 注：成员在创建招标信息或开评标准备时设置，只有加入成员才能在开评标系统进入项目
 * 成员构成：
 * 1.开评标管理员（即招标代理，通常为创建招标信息人，无须加入即可操作开标标段）
 * 2.监督人员（监督中心/部门）
 * 3.资格审查人员
 * 4.招标人代表
 * 5.评标专家
 */
@ApiModel(value = "开评标成员实例")
@Data
@Entity
public class EvalMember {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "序号", required = true, position = 2)
    private Integer sortId;

    @ApiModelProperty(value = "标段（包）uid", required = true, position = 3)
    private String sectionUid;

    @ApiModelProperty(value = "用户uid", required = true, position = 4)
    private String userUid;

    @ApiModelProperty(value = "姓名", required = true, position = 5)
    private String name;

    @ApiModelProperty(value = "角色", required = true, position = 6)
    private String role;

    @ApiModelProperty(value = "身份证号码", position = 7)
    private String idCardNo;

    @ApiModelProperty(value = "电话", position = 8)
    private String phone;

    @ApiModelProperty(value = "专业类别", position = 9)
    private String profession;

    @ApiModelProperty(value = "工作单位", position = 10)
    private String work;

    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    @ApiModelProperty(value = "得票数", position = 11)
    private Integer vote;

    @ApiModelProperty(value = "是否为评标组长", position = 11)
    private String leader;

    @ApiModelProperty(value = "是否已投票", position = 11)
    private String voted;

    @ApiModelProperty(value = "创建人uid", required = true, position = 12)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 13)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 14)
    private String createTime;
}
