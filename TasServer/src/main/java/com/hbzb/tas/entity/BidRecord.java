package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 投标记录实例
 * created by dsz at 2020.05.20
 */
@Data
@Entity
public class BidRecord {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;
    @ApiModelProperty(value = "标段uid", required = true, position = 2)
    private String sectionUid;

    @ApiModelProperty(value = "投标人uid", required = true, position = 3)
    private String bidderUid;
    @ApiModelProperty(value = "投标人名称", required = true, position = 4)
    private String bidderName;
    @ApiModelProperty(value = "联系人", position = 5)
    private String contactName;
    @ApiModelProperty(value = "联系电话", position = 6)
    private String contactPhone;

    @ApiModelProperty(value = "投标时间", position = 7)
    private String bidTime;
    @ApiModelProperty(value = "投标报价", position = 7)
    private String bidPrice;
    @ApiModelProperty(value = "备用字段1(工期)", position = 7)
    private String col1;
    @ApiModelProperty(value = "备用字段2（质量要求）", position = 7)
    private String col2;
    @ApiModelProperty(value = "备用字段3", position = 7)
    private String col3;
    @ApiModelProperty(value = "备用字段4", position = 7)
    private String col4;


    @ApiModelProperty(value = "已交工本费", position = 6)
    private String fee;
    @ApiModelProperty(value = "已交保证金", position = 7)
    private String guarantee;

    @ApiModelProperty(value = "是否签到", position = 7)
    private String signIn;
    @ApiModelProperty(value = "签到时间", position = 7)
    private String signInTime;
    @ApiModelProperty(value = "是否解密", position = 7)
    private String decrypted;
    @ApiModelProperty(value = "签到时间", position = 7)
    private String decryptedTime;

    @ApiModelProperty(value = "创建人uid", required = true, position = 7)
    private String creatorUid;
    @ApiModelProperty(value = "更新时间", required = true, position = 8)
    private String updateTime;
    @ApiModelProperty(value = "创建时间", required = true, position = 9)
    private String createTime;
}
