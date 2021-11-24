package com.feng.myhotel.controller;

import com.feng.myhotel.entities.result.CommonResult;
import com.feng.myhotel.entities.result.ResultWithButtons;
import com.feng.myhotel.entities.vo.LoginUserVO;
import com.feng.myhotel.entities.vo.RoleVO;
import com.feng.myhotel.service.QueryInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : pcf
 * @date : 2021/11/12 22:29
 */
@RestController
@Api(tags = "查询信息控制器")
public class QueryInformationController {

    @Autowired
    private QueryInformationService queryInformationService;

    @GetMapping(value = "/get/login/user/details")
    @ApiOperation(value = "拿到当前登录用户的信息", httpMethod = "GET")
    public CommonResult<LoginUserVO> getLoginUserDetails(HttpServletRequest request){
        try {
            LoginUserVO loginUserDetails = queryInformationService.getLoginUserDetails(request);
            return new CommonResult<>(200, "查询当前登录信息成功", loginUserDetails);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new CommonResult<>(400, "查询当前登录信息失败");
        }
    }

    @GetMapping(value = "/get/all/role")
    @ApiOperation(value = "查询所有角色信息", httpMethod = "GET")
    public CommonResult<List<RoleVO>> getAllRoleVO(){

        try {
            // 如果成功
            List<RoleVO> roleVOList = queryInformationService.getAllRoleVO();
            return new CommonResult<>(200, "查询成功", roleVOList);

        } catch (Exception exception) {
            // 查询失败
            exception.printStackTrace();
            return new CommonResult<>(400, "查询失败");
        }
    }

    @GetMapping(value = "/user/get/receive/button/list")
    @ApiOperation(value = "拿到button", httpMethod = "GET", notes = "请求请带token")
    public ResultWithButtons<String> getButtonList(
            HttpServletRequest request,
            @ApiParam(value = "菜单id", name = "id", example = "1") @RequestParam("id")Integer id){

        try {
            List<String> buttonList = queryInformationService.getButtonList(request, id);
            return new ResultWithButtons<>(200, "成功", null, buttonList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResultWithButtons<>(400, "失败" + exception.getMessage());
        }

    }

}
