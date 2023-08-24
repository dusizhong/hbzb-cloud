package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 招标文件实例
 * created by dusizhong at 2020.05.07
 */
@Data
@Entity
public class TenderFile {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "标段uid", required = true, position = 2)
    private String sectionUid;

    @ApiModelProperty(value = "文件名", required = true, position = 10)
    private String name;

    @ApiModelProperty(value = "招标文件价格（元）", required = true, position = 10)
    private String price;

    @ApiModelProperty(value = "招标文件发售时间", required = true, position = 10)
    private String startSellTime;

    @ApiModelProperty(value = "招标文件发售截止时间", required = true, position = 10)
    private String endSellTime;

    @ApiModelProperty(value = "招标文件获取方法", required = true, position = 10)
    private String obtainMethod;

    @ApiModelProperty(value = "原文件", required = true, position = 10)
    private String originFile;

    @ApiModelProperty(value = "加密文件", required = true, position = 10)
    private String encryptFile;

    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
