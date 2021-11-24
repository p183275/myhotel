package com.feng.myhotel.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 22:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "酒店信息封装类")
public class HotelInformationVO {

    @ApiModelProperty(value = "酒店名称")
    private String name;

    @ApiModelProperty(value = "酒店创建人")
    private String founders;

    @ApiModelProperty(value = "酒店联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "酒店logo")
    private String headerPicture;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "button列表")
    private List<String> buttonList;
}
