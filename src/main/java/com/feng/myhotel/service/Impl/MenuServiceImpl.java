package com.feng.myhotel.service.Impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.po.MenuPO;
import com.feng.myhotel.entities.vo.MenuVO;
import com.feng.myhotel.mapper.PermissionPOMapper;
import com.feng.myhotel.service.MenuService;
import com.feng.myhotel.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/14 15:46
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PermissionPOMapper permissionPOMapper;

    /**
     * 通过角色id得到菜单列表
     * @param request request请求
     * @return 菜单列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenuVO> getLoginMenuByRoleId(HttpServletRequest request) {

        // 从header中拿出token
        String token = request.getHeader("token");

        // 判断token是否为空
        if (token == null)
            throw new RuntimeException(ExceptionConstant.TOKEN_IS_NOT_NULL_EXCEPTION);

        // 从token拿到当前用户的roleId
        DecodedJWT verify = JWTUtils.verify(token);
        String roleId = verify.getClaim("roleId").asString();

        // 判断拿到的roleId是否为空
        if (roleId == null)
            throw new RuntimeException(ExceptionConstant.LOGIN_ACCOUNT_HAVE_NONE_MESSAGE);

        // 通过roleId查询菜单
        return permissionPOMapper.selectMenuByRoleId(Integer.valueOf(roleId));
    }
}
