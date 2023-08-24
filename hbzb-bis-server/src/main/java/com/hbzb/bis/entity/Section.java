package com.hbzb.bis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 标段实例
 * @author dusizhong
 * @since 2020-07-09
 */
@Data
@Entity
public class Section {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "所属项目uid", required = true, position = 2)
    private String projectUid;

    @ApiModelProperty(value = "标段uid", required = true, position = 3)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    @ApiModelProperty(value = "标段编号", required = true, position = 4)
    private String serialNo;

    @ApiModelProperty(value = "标段名称", required = true, position = 5)
    private String name;

    @ApiModelProperty(value = "所属辖区", required = true, position = 8)
    private String area;

    @ApiModelProperty(value = "采购方式（公开招标、邀请招标、竞争性谈判、竞争性磋商、询价、单一来源）", required = true, position = 7)
    private String tradeType;

    private String replyDeadline; //邀请招标的回复截止时间，用于生成邀请函

    @ApiModelProperty(value = "采购交易分类（工程类、服务类、货物类（含药品集中采购））", position = 8)
    private String tradeCategory;

    @ApiModelProperty(value = "预算总价（元）", required = true, position = 8)
    private String budget;

    @ApiModelProperty(value = "开标时间", required = true, position = 5)
    private String bidOpenTime;

    @ApiModelProperty(value = "开评标场地", required = true, position = 5)
    private String bidOpenPlace;

    @ApiModelProperty(value = "是否有投标保证金", required = true, position = 5)
    private String needGuarantee;

    @ApiModelProperty(value = "是否采用网上开评标", required = true, position = 5)
    private String bidOnline;

    @ApiModelProperty(value = "评标方法", position = 12)
    private String evalMethod;

    @ApiModelProperty(value = "标段内容", position = 5)
    private String content;

    @ApiModelProperty(value = "投标人资格条件", position = 5)
    private String qualification;

    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 14)
    private String agencyUid;

    @ApiModelProperty(value = "招标代理名称", required = true, position = 15)
    private String agencyName;

    @JsonIgnore
    @ApiModelProperty(value = "搜索关键字", required = true, position = 15)
    private String keyword;

    @ApiModelProperty(value = "状态(EDIT编辑中、SUBMIT待审核、APPROVAL审核通过、REJECT审核不通过)", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "审核人uid", required = true, position = 17)
    private String approverUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;

//    @ManyToOne
//    @JoinColumn(name = "projectUid", referencedColumnName ="uid", insertable=false, updatable=false)
//    private Project project;
}
