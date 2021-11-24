package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.UpdatePasswordVO;

/**
 * @author : pcf
 * @date : 2021/11/12 20:55
 */
public interface UserUpdatePasswordService {

    /**
     * 通过旧密码修改新密码
     * @param updatePasswordVO 修改密码封装类
     */
    void updatePasswordByOldPassword(UpdatePasswordVO updatePasswordVO);

    /**
     * 通过电话号码修改密码
     * @param updatePasswordVO 修改密码封装类
     */
    void updatePasswordByPhoneNumber(UpdatePasswordVO updatePasswordVO);

}
