package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 公告实例
 * @author dusizhong
 * @since 2020-02-05
 */
@ApiModel(value = "3.1 公告实例")
@Data
@Entity
public class Announce {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "公告uid", required = true, position = 2)
    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String uid;

    @ApiModelProperty(value = "关联标段uid", required = true, position = 5)
    private String sectionUids;

    @ApiModelProperty(value = "公告标题", required = true, position = 5)
    private String title;

    @ApiModelProperty(value = "公告类型（采购预告、采购公告、9其他）", required = true, position = 6)
    private String type;

    @ApiModelProperty(value = "公告性质（1正常公告、2变更公告、9其他）", required = true, position = 6)
    private String attribute;

    @ApiModelProperty(value = "原公告uid", position = 6)
    private String orginUid;

    @ApiModelProperty(value = "发布媒体", position = 8)
    private String media;

    @ApiModelProperty(value = "公告内容", required = true, position = 11)
    private String content;

    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    @ApiModelProperty(value = "状态(编辑中、待审核、审核通过、审核不通过)", required = true, position = 16)
    private String status;

    @ApiModelProperty(value = "发布状态(未发布、已发布、已停止)", required = true, position = 17)
    private String publish;

    @ApiModelProperty(value = "发布时间",  position = 18)
    private String publishTime;

    @ApiModelProperty(value = "招标代理uid", required = true, position = 14)
    private String agencyUid;

    @ApiModelProperty(value = "招标代理名称", required = true, position = 15)
    private String agencyName;

    @ApiModelProperty(value = "审核人（发布人）uid",  position = 18)
    private String approverUid;

    @ApiModelProperty(value = "创建人uid", required = true, position = 19)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 20)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 21)
    private String createTime;
}
