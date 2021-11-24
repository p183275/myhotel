package com.feng.myhotel.service.Impl;

import com.feng.myhotel.entities.constant.ExceptionConstant;
import com.feng.myhotel.entities.po.PermissionPO;
import com.feng.myhotel.entities.po.RolePO;
import com.feng.myhotel.entities.vo.PermissionTree;
import com.feng.myhotel.entities.vo.RoleVO;
import com.feng.myhotel.entities.vo.UserVO;
import com.feng.myhotel.mapper.PermissionPOMapper;
import com.feng.myhotel.mapper.RoleMapper;
import com.feng.myhotel.mapper.UserPOMapper;
import com.feng.myhotel.service.AuthManagerService;
import com.feng.myhotel.utils.TreeMenuUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 权限管理
 * @author : pcf
 * @date : 2021/11/20 16:18
 */
@Service
public class AuthManagerServiceImpl implements AuthManagerService {

    @Autowired
    private UserPOMapper userPOMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionPOMapper permissionPOMapper;

    /**
     * 拿到所有用户信息
     * @param pageIndex 当前页面
     * @param pageSize 页面显示数据的最大数量
     * @return 用户信息列表
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<UserVO> getUserVO(Integer pageIndex, Integer pageSize) {

        // 开始分页
        PageHelper.startPage(pageIndex, pageSize);

        // 查询用户信息
        List<UserVO> userVOList = userPOMapper.selectUserVOList();

        // 放入分页数据追踪
        PageInfo<UserVO> pageInfo = new PageInfo<>(userVOList);

        return pageInfo;
    }

    /**
     * 更新用户角色
     * @param map 用户角色id封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateRoleOfUser(Map<String, String> map) {

        // 拿到用户id和角色id
        String userId = map.get("userId");
        String roleId = map.get("roleId");

        // 判断
        if (userId == null || roleId == null)
            throw new RuntimeException("数据不能为空");

        // 通过用户id改变用户角色
        userPOMapper.updateRoleByUserId(Long.valueOf(userId), Integer.parseInt(roleId));
    }

    /**
     * 删除用户
     * @param map 封装用户userIdList的map
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUser(Map<String, List<Long>> map) {

        // 拿到用户id的列表
        List<Long> userIdList = map.get("userIdList");

        // 判断
        if (userIdList == null)
            throw new RuntimeException("数据不能为空");

        // 调用方法从数据库中删除
        userPOMapper.deleteUserByIdList(userIdList);
    }

    /**
     * 增加角色
     * @param roleVO 角色封装类
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addRole(RoleVO roleVO) {

        // 判断
        if (roleVO == null)
            throw new RuntimeException("数据不能为空");

        // 创建po对象并复制属性
        RolePO rolePO = new RolePO();
        BeanUtils.copyProperties(roleVO, rolePO);

        // 拿到当前时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date time = calendar.getTime();

        // 修改po对象中的创建时间
        rolePO.setCreateDate(time);

        // 存入数据库
        roleMapper.insertRole(rolePO);
    }

    /**
     * 删除角色
     * @param map 封装角色id的map
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRole(Map<String, List<Integer>> map) {

        // 拿出角色id
        List<Integer> roleIdList = map.get("roleIdList");

        // 判断是否为空
        if (roleIdList == null || roleIdList.size() == 0)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 删除角色权限中间表
        roleMapper.deleteAllPermissionsByRoleIdList(roleIdList);

        // 从数据库中删除
        roleMapper.deleteRoleByIdList(roleIdList);
    }

    /**
     * 修改角色权限
     * @param map 封装角色id和权限id的map
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateAuthOfRole(Map<String, List<Integer>> map) {

        // 拿到角色id
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        // 拿到权限id列表
        List<Integer> authIdList = map.get("authIdList");

        if (roleId == null || authIdList == null)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 判断
        if (roleIdList.size() == 0 || authIdList.size() == 0)
            throw new RuntimeException(ExceptionConstant.DATA_IS_NOT_NULL_EXCEPTION);

        // 删除该角色的所有权限
        roleMapper.deleteAllPermissionsByRoleId(roleId);

        // 修改角色权限中间表
        roleMapper.insertRelationByRoleId(roleId, authIdList);

    }

    /**
     * 拿到权限菜单树形结构
     * @return 树形结构
     */
    @Override
    @Transactional(readOnly = true)
    public List<PermissionTree> getPermissionTree() {

        // 拿到所有权限
        List<PermissionTree> permissionTreeList = permissionPOMapper.selectPermissionTree();

        // 拿到list类型的树形列表
        List<PermissionTree> permissionTreeListRes =
                TreeMenuUtils.getPermissionTreeList(permissionTreeList);


        return permissionTreeListRes;
    }
}
