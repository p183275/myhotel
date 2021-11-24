package com.feng.myhotel.service.Impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.po.RolePO;
import com.feng.myhotel.entities.po.UserPO;
import com.feng.myhotel.entities.vo.LoginUserVO;
import com.feng.myhotel.entities.vo.RoleVO;
import com.feng.myhotel.entities.vo.UserVO;
import com.feng.myhotel.mapper.PermissionPOMapper;
import com.feng.myhotel.mapper.RoleMapper;
import com.feng.myhotel.mapper.UserPOMapper;
import com.feng.myhotel.service.QueryInformationService;
import com.feng.myhotel.utils.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/12 22:30
 */
@Service
@Transactional(readOnly = true)
public class QueryInformationServiceImpl implements QueryInformationService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserPOMapper userPOMapper;
    @Autowired
    private PermissionPOMapper permissionPOMapper;

    /**
     * 查询所有角色信息
     * @return 角色列表
     */
    @Override
    public List<RoleVO> getAllRoleVO() {

        return roleMapper.selectAllRoleVO();
    }

    /**
     * 拿到登录用户的详细信息
     * @param request 请求
     * @return 登录用户信息
     */
    @Override
    public LoginUserVO getLoginUserDetails(HttpServletRequest request) {

        // 从请求中拿到token
        String token = request.getHeader("token");

        // 拿到用户id
        DecodedJWT verify = JWTUtils.verify(token);
        String userId = verify.getClaim("userId").asString();

        // 通过id查询po对象
        UserPO userPO = userPOMapper.selectByPrimaryKey(Long.valueOf(userId));

        // 复制属性
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(userPO, loginUserVO);

        // 通过roleId去查询role
        RolePO rolePO = roleMapper.selectAllRolePOById(loginUserVO.getRoleId());

        // 将RoleName写进login对象
        String roleName = rolePO.getRoleName();
        loginUserVO.setRoleName(roleName);

        return loginUserVO;
    }

    /**
     * 拿到按钮列表
     * @param request 请求
     * @param map 封装按钮的map
     * @return 按钮列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getButtonList(HttpServletRequest request , Map<String, String> map) {

        // 拿到token
        String token = request.getHeader("token");

        // 拿到当前角色的id
        DecodedJWT verify = JWTUtils.verify(token);
        String roleId = verify.getClaim("roleId").asString();

        // 从map中拿到permissionId
        String permissionId = map.get("id");

        if (permissionId == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 取出按钮信息
        List<String> buttonList = permissionPOMapper.selectButtonList(Integer.parseInt(permissionId),
                Integer.parseInt(roleId));

        return buttonList;
    }

    /**
     * 取出按钮信息(重载)
     * @param request 请求
     * @param permissionId 权限id
     * @return 按钮列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getButtonList(HttpServletRequest request, Integer permissionId) {

        // 拿到token
        String token = request.getHeader("token");

        // 拿到当前角色的id
        DecodedJWT verify = JWTUtils.verify(token);
        String roleId = verify.getClaim("roleId").asString();

        // 取出按钮信息
        List<String> buttonList = permissionPOMapper.selectButtonList(permissionId, Integer.parseInt(roleId));

        return buttonList;
    }
}
