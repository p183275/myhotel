package com.feng.myhotel.service;

import com.feng.myhotel.entities.po.MenuPO;
import com.feng.myhotel.entities.vo.MenuVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 15:46
 */
public interface MenuService {

    /**
     * 通过角色id得到当前用户的菜单
     * @param request request请求
     * @return 菜单列表
     */
    List<MenuVO> getLoginMenuByRoleId(HttpServletRequest request);

}
