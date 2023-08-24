package com.hbzb.tas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 电子件实例
 * created by dusizhong at 2020.02.13
 */
@ApiModel("电子件实例")
@Data
@Entity
public class Material {

    @ApiModelProperty(value = "主键", required = true, position = 1)
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "uid", required = true, position = 4)
    private String uid;

    @ApiModelProperty(value = "所属项（PROJECT项目、TENDER招标文件、ANNOUNCE预告公告）", required = true, position = 3)
    private String belong;

    @ApiModelProperty(value = "所属项uid", required = true, position = 4)
    private String belongUid;

    @ApiModelProperty(value = "材料名称", required = true, position = 5)
    private String name;

    @ApiModelProperty(value = "原始名称", required = true, position = 5)
    private String originName;

    private String fileName;

    @ApiModelProperty(value = "文件类型(word,excel,pdf,jpg,png)", required = true, position = 5)
    private String type;

    @ApiModelProperty(value = "文件大小（kb）", required = true, position = 6)
    private String size;

    private String sha512;

    private Integer hits;

    @ApiModelProperty(value = "创建人uid", required = true, position = 9)
    private String creatorUid;

    @ApiModelProperty(value = "创建人", position = 8)
    private String creatorName;

    @ApiModelProperty(value = "更新时间", required = true, position = 10)
    private String updateTime;

    @ApiModelProperty(value = "创建时间", required = true, position = 11)
    private String createTime;
}
