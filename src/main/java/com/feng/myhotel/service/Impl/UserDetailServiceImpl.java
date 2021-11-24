package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.po.UserPO;
import com.feng.myhotel.entities.vo.UserVO;
import com.feng.myhotel.mapper.PermissionPOMapper;
import com.feng.myhotel.mapper.UserPOMapper;
import com.feng.myhotel.security.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证方法
 * @author : pcf
 * @date : 2021/11/11 18:57
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserPOMapper userPOMapper;
    @Autowired
    private PermissionPOMapper permissionPOMapper;

    @Override
    public UserDetails loadUserByUsername(String loginAccount) throws UsernameNotFoundException {

        // 从数据库中查出数据
        UserPO userPO = userPOMapper.selectByLoginAccount(loginAccount);

        // 复制数据
        SecurityUser securityUser = new SecurityUser();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPO, securityUser);
        BeanUtils.copyProperties(userPO, userVO);

        // 通过角色id去查询权限
        List<String> permissionList = permissionPOMapper.selectPermissionValueByRoleId(userPO.getRoleId());

        // 放置数据
        securityUser.setCurrentUserInfo(userVO);
        securityUser.setPermissionValueList(permissionList);

        return securityUser;
    }

}
