package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.result.ResultWithButtons;
import com.feng.myhotel.entities.vo.PermissionTree;
import com.feng.myhotel.entities.vo.RoleVO;
import com.feng.myhotel.entities.vo.RoomOrderVO;
import com.feng.myhotel.entities.vo.UserVO;
import com.feng.myhotel.service.AuthManagerService;
import com.feng.myhotel.service.QueryInformationService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pcf
 * @date : 2021/11/20 16:33
 */
@RestController
@Api(tags = "权限管理")
public class AuthManagerController {

    @Autowired
    private AuthManagerService authManagerService;
    @Autowired
    private QueryInformationService queryInformationService;

    @GetMapping(value = "/auth/manage/get/user/list")
    @ApiOperation(value = "用户管理：拿到所有用户列表", httpMethod = "GET", notes = "请求请带token")
    public ResultWithButtons<PageInfo<UserVO>> getUserDetails(
            HttpServletRequest request,
            @ApiParam(value = "每个页面显示的数据", name = "pageSize", example = "1")
                @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
            @ApiParam(value = "当前页面的页数", name = "pageIndex", example = "1")
                @RequestParam(value = "pageIndex", defaultValue = "1", required = false)Integer pageIndex,
            @ApiParam(value = "当前菜单的id", name = "id", example = "1")
                @RequestParam(value = "id")Integer id){

        try {
            PageInfo<UserVO> userList = authManagerService.getUserVO(pageIndex, pageSize);
            List<String> buttonList = queryInformationService.getButtonList(request, id);
            return new ResultWithButtons<>(200, "查询用户信息成功", userList, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "查询用户信息失败");
        }
    }

    @PostMapping(value = "/auth/manage/update/user/role")
    @ApiOperation(value = "用户管理：根据用户Id和角色Id修改用户角色", httpMethod = "POST")
    public CommonResult<String> updateRoleByRoleId(
            @ApiParam(value = "userId和roleId 格式：'userId':'1','roleId':'2'", name = "map", example = "'userId':'1','roleId':'2'")
                @RequestBody Map<String, String> map){

        try {
            authManagerService.updateRoleOfUser(map);
            return new CommonResult<>(200, "修改成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "修改失败");
        }
    }

    @PostMapping(value = "/auth/manage/delete/user")
    @ApiOperation(value = "用户管理：删除用户", httpMethod = "POST", notes = "请求请带token")
    public CommonResult<String> deleteUserByIdList(
            @ApiParam(value = "用户id列表格式：'userIdList':[1,2,3......]", name = "map", example = "'userIdList':[1,2,3......]")
                @RequestBody Map<String, List<Long>> map){

        try {
            authManagerService.deleteUser(map);
            return new CommonResult<>(200, "删除用户成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "删除用户失败");
        }

    }

    @PostMapping(value = "/auth/manage/add/role")
    @ApiOperation(value = "角色管理：增加角色", httpMethod = "POST", notes = "请求请带token")
    public CommonResult<String> addRole(
            @ApiParam(value = "id 和 createDate不需要传", name = "roleVO")
                @RequestBody RoleVO roleVO){

        try {
            authManagerService.addRole(roleVO);
            return new CommonResult<>(200, "增加角色成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "增加角色失败");
        }

    }

    @PostMapping(value = "/auth/manage/delete/role")
    @ApiOperation(value = "角色管理：删除角色", httpMethod = "POST", notes = "请求请带token")
    public CommonResult<String> deleteRoleByIdList(
            @ApiParam(value = "角色id列表格式：'roleIdList':[1,2,3......]", name = "map", example = "'roleIdList':[1,2,3......]")
                @RequestBody Map<String, List<Integer>> map){

        try {
            authManagerService.deleteRole(map);
            return new CommonResult<>(200, "删除角色成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "删除角色失败");
        }

    }

    @PostMapping(value = "/auth/manage/update/auth/of/role")
    @ApiOperation(value = "角色管理：修改角色权限", httpMethod = "POST", notes = "请求请带token")
    public CommonResult<String> updateAuthOfRole(
            @ApiParam(value = "参数roleId，authIdList：格式 {'roleId':[以数组的形式放入1个],'authIdList':[权限的id]'}", name = "map")
                @RequestBody Map<String, List<Integer>> map){

        try {
            authManagerService.updateAuthOfRole(map);
            return new CommonResult<>(200, "修改角色权限成功");

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "修改角色权限失败");
        }

    }

    @GetMapping(value = "/auth/manage/get/permission/tree")
    @ApiOperation(value = "角色管理：拿到角色权限树形结构", httpMethod = "GET", notes = "请求请带token")
    public CommonResult<List<PermissionTree>> getPermissionTree(){

        try {
            List<PermissionTree> permissionTreeList = authManagerService.getPermissionTree();
            return new CommonResult<>(200, "查询成功", permissionTreeList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "查询失败");
        }
    }

}
