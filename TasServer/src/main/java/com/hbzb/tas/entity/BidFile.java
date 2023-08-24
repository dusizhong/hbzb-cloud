package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 投标文件实例
 * created by dsz at 2020.05.22
 */
@Data
@Entity
public class BidFile {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "标段uid", required = true, position = 2)
    private String sectionUid;

    @ApiModelProperty(value = "投标人uid", required = true, position = 3)
    private String bidderUid;

    @ApiModelProperty(value = "投标人名称", required = true, position = 3)
    private String bidderName;

    @ApiModelProperty(value = "投标文件", required = true, position = 4)
    private String originFile;

    @ApiModelProperty(value = "解密密码", required = true, position = 4)
    private String decryptPwd;

    @ApiModelProperty(value = "上传完成时间", required = true, position = 5)
    private String uploadedTime;

    @ApiModelProperty(value = "解密后文件", required = true, position = 6)
    private String decryptFile;

    @ApiModelProperty(value = "解密完成时间", required = true, position = 7)
    private String decryptTime;

    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    @ApiModelProperty(value = "创建人ip", required = true, position = 9)
    private String creatorIp;

    @ApiModelProperty(value = "创建人mac地址", required = true, position = 10)
    private String creatorMac;

    @ApiModelProperty(value = "创建人uid", required = true, position = 11)
    private String creatorUid;

    @ApiModelProperty(value = "更新时间", required = true, position = 12)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 13)
    private String createTime;
}
