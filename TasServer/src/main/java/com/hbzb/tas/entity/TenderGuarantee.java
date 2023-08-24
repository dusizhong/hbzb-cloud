package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 投标保证金设置实例
 * created by dsz at 2020.05.31
 */
@ApiModel(value = "1.4 投标保证金设置实例")
@Data
@Entity
public class TenderGuarantee {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "标段uid", required = true, position = 3)
    private String sectionUid;

    @ApiModelProperty(value = "投标保证金金额（元）", required = true, position = 6)
    private String amount;

    @ApiModelProperty(value = "保证金缴纳截止时间", required = true, position = 10)
    private String deadline;

    @ApiModelProperty(value = "缴纳方式(现金缴纳、银行保函、担保机构保函)", required = true, position = 11)
    private String type;

    @ApiModelProperty(value = "保证金缴纳开户行", position = 11)
    private String bankName;

    @ApiModelProperty(value = "保证金缴纳户名", position = 12)
    private String accountName;

    @ApiModelProperty(value = "保证金缴纳账号", position = 12)
    private String accountNo;

    @ApiModelProperty(value = "必须基本户支付", required = true, position = 12)
    private String basicPay;

    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    @ApiModelProperty(value = "创建人uid", required = true, position = 17)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 18)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 19)
    private String createTime;
}
