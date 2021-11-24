package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.MenuPO;
import com.feng.myhotel.entities.po.PermissionPO;
import com.feng.myhotel.entities.vo.MenuVO;
import com.feng.myhotel.entities.vo.PermissionTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/11 19:25
 */
@Mapper
public interface PermissionPOMapper {

    /**
     * 通过roleId查询权限信息
     * @param roleId 角色id
     * @return 权限信息
     */
    List<String> selectPermissionValueByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询到所有权限信息
     * @return 权限列表
     */
    List<PermissionPO> selectAllPermission();

    /**
     * 查询到所有权限信息
     * @return 树形结构
     */
    List<PermissionTree> selectPermissionTree();

    /**
     * 通过roleId查询权限信息
     * @param roleId 角色id
     * @return 权限信息
     */
    List<MenuVO> selectMenuByRoleId(@Param("roleId") Integer roleId);

    List<String> selectButtonList(@Param("permissionId") Integer permissionId,
                                  @Param("roleId") Integer roleId);

}
