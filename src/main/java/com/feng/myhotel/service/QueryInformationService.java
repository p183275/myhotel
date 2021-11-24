package com.feng.myhotel.service;

import com.feng.myhotel.entities.vo.LoginUserVO;
import com.feng.myhotel.entities.vo.RoleVO;
import com.feng.myhotel.entities.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/12 22:29
 */
public interface QueryInformationService {

    /**
     * 查询所有角色信息
     * @return 角色列表
     */
    List<RoleVO> getAllRoleVO();

    /**
     * 拿到当前登录用户的信息
     * @param request 请求
     * @return 登录用户的信息封装
     */
    LoginUserVO getLoginUserDetails(HttpServletRequest request);

    /**
     * 拿到按钮列表
     * @param request 请求
     * @param map 封装按钮的map
     * @return 按钮列表
     */
    List<String> getButtonList(HttpServletRequest request , Map<String, String> map);

    /**
     * 拿到按钮列表(重载)
     * @param request 请求
     * @param permissionId 权限id
     * @return 按钮列表
     */
    List<String> getButtonList(HttpServletRequest request , Integer permissionId);
}
