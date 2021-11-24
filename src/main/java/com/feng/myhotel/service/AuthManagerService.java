package com.feng.myhotel.service;


import com.feng.myhotel.entities.vo.PermissionTree;
import com.feng.myhotel.entities.vo.RoleVO;
import com.feng.myhotel.entities.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 权限管理
 * @author : pcf
 * @date : 2021/11/20 16:18
 */
public interface AuthManagerService {

    /**
     * 拿到所有用户信息
     * @param pageIndex 当前页面
     * @param pageSize 页面显示数据的最大数量
     * @return 用户信息列表
     */
    PageInfo<UserVO> getUserVO(Integer pageIndex, Integer pageSize);

    /**
     * 更新用户角色
     * @param map 用户角色id封装类
     */
    void updateRoleOfUser(Map<String, String> map);

    /**
     * 删除用户
     * @param map 封装用户userIdList的map
     */
    void deleteUser(Map<String, List<Long>> map);

    /**
     * 增加角色
     * @param roleVO 角色封装类
     */
    void addRole(RoleVO roleVO);

    /**
     * 删除角色
     * @param map 封装角色id的map
     */
    void deleteRole(Map<String, List<Integer>> map);

    /**
     * 修改角色权限
     * @param map 封装角色id和权限id的map
     */
    void updateAuthOfRole(Map<String, List<Integer>> map);

    /**
     * 拿到权限菜单树形结构
     * @return 树形结构
     */
    List<PermissionTree> getPermissionTree();
}
