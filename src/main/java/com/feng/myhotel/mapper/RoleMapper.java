package com.feng.myhotel.mapper;

import com.feng.myhotel.entities.po.RolePO;
import com.feng.myhotel.entities.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/11 22:32
 */
@Mapper
public interface RoleMapper {

    /**
     * 查询所有角色信息
     * @return 角色信息列表
     */
    List<RoleVO> selectAllRoleVO();

    /**
     * 通过roleId查询对象
     * @param roleId 主键
     * @return 对象
     */
    RolePO selectAllRolePOById(@Param("roleId")Integer roleId);

    /**
     * 增加角色
     * @param rolePO 角色信息封装类
     */
    void insertRole(@Param("rolePO") RolePO rolePO);

    void insertRelationByRoleId(@Param("roleId") Integer roleId,
                                @Param("permissionIdList") List<Integer> permissionIdList);

    /**
     * 删除role
     * @param roleIdList idList
     */
    void deleteRoleByIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * 删除该角色的所有权限
     * @param roleId 角色id
     */
    void deleteAllPermissionsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 删除角色权限中间表
     * @param roleIdList 角色id
     */
    void deleteAllPermissionsByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    String getRoleNameByRoleId(@Param("roleId") Integer roleId);

}
