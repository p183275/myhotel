package com.feng.myhotel.entities.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author : pcf
 * @date : 2021/11/14 22:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelInformationPO {

    private String name;

    private String founders;

    private String phoneNumber;

    private String headerPicture;

    private Date updateTime;

    private Date createTime;

}
